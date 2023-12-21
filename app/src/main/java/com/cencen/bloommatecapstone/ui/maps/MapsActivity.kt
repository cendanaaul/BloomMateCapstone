package com.cencen.bloommatecapstone.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cencen.bloommatecapstone.R
import com.cencen.bloommatecapstone.data.Libraries
import com.cencen.bloommatecapstone.data.call.LocationCoordinate
import com.cencen.bloommatecapstone.data.model.DataMaps
import com.cencen.bloommatecapstone.data.model.Seller

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.cencen.bloommatecapstone.databinding.ActivityMapsBinding
import com.cencen.bloommatecapstone.util.ViewModelProviderFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mapsViewModel: MapsViewModel
    private val llBoundBuild = LatLngBounds.builder()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initializeVM()

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotBlank()) {
                        getFlowerSeller(it)
                        return true
                    }
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // You can handle text changes here if needed
                return false
            }
        })


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        applyMapsTheme()
        getPosition()

    }

    private fun applyMapsTheme() {
        try {
            val successful =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.maps_style))
            if (!successful) {
                Log.e(TAG, "Style applying failed")
            }
        } catch (exc: Resources.NotFoundException) {
            Log.e(TAG, "Style not found. Error: ", exc)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { grant: Boolean ->
            if (grant) {
                getPosition()
            }
        }

    private fun getPosition() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun initializeVM() {
        val fact: ViewModelProviderFactory = ViewModelProviderFactory.getInstance(this)
        mapsViewModel = ViewModelProvider(this, fact)[MapsViewModel::class.java]
    }

    private fun getFlowerSeller(flowerName: String) {
        mapsViewModel.getFlowerSeller(flowerName).observe(this, Observer { result ->
            when (result) {
                is Libraries.Success -> {
                    showMarkerSeller(result.data.data)
                }
                is Libraries.Error -> {
                    showToast(result.error)
                }
                is Libraries.Loading -> {
                    // Handle loading state if needed
                }
                else -> { }
            }
        })
    }

    private fun showMarkerSeller(data: List<DataMaps>) {
        for (flowerData in data) {
            val location = flowerData.seller.locationCoordinate
            if (location != null && location.latitude != null && location.longitude != null) {
                val sellerLatLng = LatLng(location.latitude, location.longitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(sellerLatLng)
                        .title(flowerData.seller.name)
                        .snippet(flowerData.localName)
                )

                llBoundBuild.include(sellerLatLng)
            }
        }

        // Set camera position to include all markers
        val bounds = llBoundBuild.build()
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }


    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}

/*
// Add a marker in Sydney and move the camera
val sydney = LatLng(-34.0, 151.0)
mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
