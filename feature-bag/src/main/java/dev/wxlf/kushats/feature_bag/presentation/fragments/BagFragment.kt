package dev.wxlf.kushats.feature_bag.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_bag.R
import dev.wxlf.kushats.feature_bag.common.ProductCount
import dev.wxlf.kushats.feature_bag.databinding.FragmentBagBinding
import dev.wxlf.kushats.feature_bag.domain.usecases.ChangeProductCountUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_bag.domain.usecases.mapToDisplayable
import dev.wxlf.kushats.feature_bag.presentation.adapterdelegates.DisplayableItem
import dev.wxlf.kushats.feature_bag.presentation.adapterdelegates.productAdapterDelegate
import dev.wxlf.kushats.feature_bag.presentation.viewmodels.BagViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class BagFragment : Fragment() {

    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: BagViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLocation: Location? = null

    private var firstSuccess = false
    private lateinit var adapter: ListDelegationAdapter<List<DisplayableItem>>
    private var fetchDishesAlertDialog: AlertDialog? = null
    private var changeAlertDialog: AlertDialog? = null

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[BagViewModel::class.java]
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        adapter = ListDelegationAdapter(
            productAdapterDelegate(
                decrementClickListener = {
                    viewModel.changeProductCount(
                        it,
                        ProductCount.DECREMENT
                    )
                },
                incrementClickListener = {
                    viewModel.changeProductCount(
                        it,
                        ProductCount.INCREMENT
                    )
                }
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchLocation()

        val formatter = SimpleDateFormat(
            getString(R.string.date_format),
            resources.configuration.locales.get(0)
        )
        binding.date.text = formatter.format(Date())

        binding.payButton.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.pay), Toast.LENGTH_LONG).show()
        }

        binding.productsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.productsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchDishesState.collect { result ->
                when (result) {
                    is FetchDishesUseCase.Result.Failure -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.GONE
                        binding.payButton.visibility = View.GONE
                        if (fetchDishesAlertDialog == null) {
                            fetchDishesAlertDialog = MaterialAlertDialogBuilder(requireContext())
                                .setTitle(R.string.error_dialog_title)
                                .setMessage(result.msg)
                                .setPositiveButton(R.string.retry_error_dialog_button) { _, _ ->
                                }
                                .setNegativeButton(R.string.back_error_dialog_button) { _, _ ->
                                    findNavController().popBackStack()
                                }
                                .setOnDismissListener {
                                    viewModel.fetchDishes()
                                }
                                .create()
                        }
                        fetchDishesAlertDialog?.show()
                    }

                    FetchDishesUseCase.Result.Loading -> {
                        if (!firstSuccess) {
                            binding.circularLoader.visibility = View.VISIBLE
                            binding.productsList.visibility = View.GONE
                            binding.payButton.visibility = View.GONE
                        }
                    }

                    is FetchDishesUseCase.Result.Success -> {
                        firstSuccess = true
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.VISIBLE
                        binding.payButton.visibility = View.VISIBLE

                        val items = result.dishes.mapToDisplayable(result.products)
                        adapter.items = items
                        adapter.notifyDataSetChanged()

                        val sum = items.sumOf { it.count * it.price }
                        binding.payButton.text =
                            "${getString(R.string.pay_button)} $sum ${getString(R.string.currency)}"
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.changeProductCountState.collect { result ->
                when (result) {
                    is ChangeProductCountUseCase.Result.Failure -> {
                        if (changeAlertDialog == null) {
                            changeAlertDialog = MaterialAlertDialogBuilder(requireContext())
                                .setTitle(R.string.error_dialog_title)
                                .setMessage(result.msg)
                                .setNegativeButton(getString(R.string.close_error_dialog_button)) { _, _ ->
                                }
                                .create()
                        }
                        changeAlertDialog?.show()
                    }

                    ChangeProductCountUseCase.Result.Loading -> {}
                    ChangeProductCountUseCase.Result.Success -> {
                        viewModel.fetchDishes()
                    }
                }
            }
        }

        viewModel.fetchDishes()
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission
                (
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), locationPermissions, 101
            )
            return
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(requireActivity(), locationPermissions, 101)
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location == null)
                requestNewLocationData()
            else {
                val geocoder =
                    Geocoder(requireActivity().baseContext, resources.configuration.locales.get(0))
                @Suppress("DEPRECATION") val addresses =
                    geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses != null) {
                    if (addresses.size > 0)
                        binding.userCity.text = addresses[0].locality
                }
            }
        }
    }

    private fun requestNewLocationData() {
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                currentLocation = location
            }
        }
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, 5).build()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), locationPermissions, 101)
            return
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(requireContext(), "Location cannot be determined", Toast.LENGTH_SHORT)
                .show()
            ActivityCompat.requestPermissions(requireActivity(), locationPermissions, 101)
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        requireActivity().supportFragmentManager.popBackStack()
        super.onDetach()
    }
}