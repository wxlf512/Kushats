package dev.wxlf.kushats.feature_main.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
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
import dev.wxlf.kushats.core.DeepLinks
import dev.wxlf.kushats.feature_main.R
import dev.wxlf.kushats.feature_main.databinding.FragmentMainBinding
import dev.wxlf.kushats.feature_main.domain.usecases.FetchCategoriesUseCase
import dev.wxlf.kushats.feature_main.domain.usecases.mapToDisplayable
import dev.wxlf.kushats.feature_main.presentation.adapterdelegates.DisplayableItem
import dev.wxlf.kushats.feature_main.presentation.adapterdelegates.categoryAdapterDelegate
import dev.wxlf.kushats.feature_main.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var currentLocation: Location? = null

    private lateinit var adapter: ListDelegationAdapter<List<DisplayableItem>>

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        adapter = ListDelegationAdapter(
            categoryAdapterDelegate {
                findNavController().navigate(Uri.parse(DeepLinks.CATEGORY.link + it.id))
            }
        )

        viewModel.fetchCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatter = SimpleDateFormat(
            getString(R.string.date_format),
            resources.configuration.locales.get(0)
        )
        binding.date.text = formatter.format(Date())
        fetchLocation()

        binding.categoriesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.categoriesList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchCategoriesState.collect { result ->
                when (result) {
                    is FetchCategoriesUseCase.Result.Failure -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.categoriesList.visibility = View.GONE
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle(R.string.error_dialog_title)
                            .setMessage(result.msg)
                            .setPositiveButton(R.string.retry_error_dialog_button) { _, _ ->
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
    }

    @Suppress("DEPRECATION")
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
                val addresses =
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