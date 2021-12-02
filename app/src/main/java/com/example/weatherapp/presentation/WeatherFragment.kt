package com.example.weatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.presentation.adapter.WeatherForecastAdapter
import com.example.weatherapp.presentation.viewmodel.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val LOCATION_PERMISSION_ID = 10

class WeatherFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val greeting = binding.greeting
        val searchView = binding.searchView
        val tempToday = binding.tempResult
        val cityName = binding.cityName
        val currentDay = binding.day
        val recyclerView = binding.forecastResult
        val currentLocation = binding.currentLocation

        viewModel.greeting.observe(viewLifecycleOwner, { result ->
            greeting.isVisible = result
        })

        viewModel.forecastToday.observe(viewLifecycleOwner, { result ->
            cityName.text = result.name
        })

        viewModel.currentDay.observe(viewLifecycleOwner, { result ->
            currentDay.text = result
        })

        viewModel.tempToday.observe(viewLifecycleOwner, { result ->
            tempToday.text = result
        })

        viewModel.forecastWeek.observe(viewLifecycleOwner, { result ->

            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = WeatherForecastAdapter(result)
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getCoord(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setVisible()
                viewModel.getForecastToday(query)
                viewModel.getForecastWeek()
                return false
            }
        })

        currentLocation.setOnClickListener {
            viewModel.setVisible()
            getForecastCurrentLocation()
        }
    }

    private fun getForecastCurrentLocation() {
        if (checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location == null) {
                    Toast.makeText(
                        requireContext(),
                        "Try again", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.getForecastTodayCurrentLocation(
                        lat = location.latitude.toString(),
                        lon = location.longitude.toString()
                    )
                    viewModel.getForecastWeekCurrentLocation(
                        lat = location.latitude.toString(),
                        lon = location.longitude.toString()
                    )
                }
            }
        }
    }

    private fun checkPermission(vararg perm: String): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(requireContext(), it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            if (perm.toList().any {
                    ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), it)
                }
            ) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Permission")
                    .setMessage("Permission needed!")
                    .setPositiveButton("Ok") { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(), perm, LOCATION_PERMISSION_ID
                        )
                    }
                    .setNegativeButton("No") { _, _ -> }
                    .create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(), perm, LOCATION_PERMISSION_ID)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getForecastCurrentLocation()
        } else {
            Toast.makeText(
                requireContext(),
                "Please give location permission", Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}