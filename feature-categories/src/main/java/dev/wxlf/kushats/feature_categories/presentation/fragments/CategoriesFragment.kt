package dev.wxlf.kushats.feature_categories.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
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
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.core.DeepLinks
import dev.wxlf.kushats.feature_categories.R
import dev.wxlf.kushats.feature_categories.databinding.FragmentCategoriesBinding
import dev.wxlf.kushats.feature_categories.domain.usecases.FetchCategoriesUseCase
import dev.wxlf.kushats.feature_categories.domain.usecases.mapToDisplayable
import dev.wxlf.kushats.feature_categories.presentation.adapterdelegates.DisplayableItem
import dev.wxlf.kushats.feature_categories.presentation.adapterdelegates.categoryAdapterDelegate
import dev.wxlf.kushats.feature_categories.presentation.viewmodels.CategoriesViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: CategoriesViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLocation: Location? = null

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[CategoriesViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fetchLocation()

        val formatter = SimpleDateFormat("d MMMM, y", resources.configuration.locales.get(0))
        binding.date.text = formatter.format(Date())

        val adapter = ListDelegationAdapter<List<DisplayableItem>>(
            categoryAdapterDelegate {
                findNavController().navigate(Uri.parse(DeepLinks.CATALOG.link + it.id))
            }
        )

        binding.categoriesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.categoriesList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchCategoriesState.collect { result ->
                when (result) {
                    is FetchCategoriesUseCase.Result.Failure -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.categoriesList.visibility = View.GONE
                        AlertDialog.Builder(requireContext())
                            .setTitle(getString(R.string.error_dialog_title))
                            .setMessage(result.msg)
                            .setPositiveButton(
                                getString(R.string.retry_error_dialog_button)
                            ) { dialog, _ ->
                                dialog?.dismiss()
                            }
                            .setOnDismissListener {
                                viewModel.fetchCategories()
                            }
                            .show()
                    }

                    FetchCategoriesUseCase.Result.Loading -> {
                        binding.circularLoader.visibility = View.VISIBLE
                        binding.categoriesList.visibility = View.GONE
                    }

                    is FetchCategoriesUseCase.Result.Success -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.categoriesList.visibility = View.VISIBLE
                        adapter.items = result.categories.mapToDisplayable()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        viewModel.fetchCategories()
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
}