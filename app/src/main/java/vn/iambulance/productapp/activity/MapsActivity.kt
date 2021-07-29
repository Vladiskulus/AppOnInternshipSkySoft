package vn.iambulance.productapp.activity

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import vn.iambulance.productapp.*
import vn.iambulance.productapp.R
import vn.iambulance.productapp.databinding.ActivityMapsBinding
import vn.iambulance.productapp.view_model.MapsViewModel

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private lateinit var viewModel: MapsViewModel
    private lateinit var binding: ActivityMapsBinding
    private lateinit var activity: MapsActivity

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                locationPermissionGranted = true
                viewModel.updateLocationUI(map)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this
        viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModel.getDeviceLocation(map, lastKnownLocation, fusedLocationProviderClient)
        viewModel.mapStatus.observe(this, {
            when (it) {
                StatusEnum.SUCCESS.status -> {
                    activity toast it
                }
                StatusEnum.ERROR.status -> {
                    activity toast it
                    activity nextActivity MapsActivity::class.java
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
        cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        if (locationPermissionGranted) {
            viewModel.updateLocationUI(map)
            viewModel.getDeviceLocation(map, lastKnownLocation, fusedLocationProviderClient)
        } else {
            try {
                map?.isMyLocationEnabled = false
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }
            map?.uiSettings?.isMyLocationButtonEnabled = false
            lastKnownLocation = null
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(-33.8523341, 151.2106085),
                    15f
                )
            )
            map?.uiSettings?.isMyLocationButtonEnabled = false
        }
    }

    companion object {
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}