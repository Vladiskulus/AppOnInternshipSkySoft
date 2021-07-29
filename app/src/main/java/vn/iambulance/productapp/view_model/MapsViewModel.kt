package vn.iambulance.productapp.view_model

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import vn.iambulance.productapp.StatusEnum

class MapsViewModel : ViewModel() {

    private val status = MutableLiveData<String>()
    var mapStatus = status as LiveData<String>

    fun getDeviceLocation(
        map: GoogleMap?,
        lastKnownLocation: Location?,
        client: FusedLocationProviderClient
    ) {
        try {
            val locationResult = client.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        map?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation.latitude,
                                    lastKnownLocation.longitude
                                ), 15f
                            )
                        )
                        status.value = StatusEnum.SUCCESS.status
                    }
                } else {
                    map?.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(
                                -33.8523341,
                                151.2106085
                            ), 15f
                        )
                    )
                    map?.uiSettings?.isMyLocationButtonEnabled = false
                    status.value = StatusEnum.ERROR.status
                }
            }

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    fun updateLocationUI(map: GoogleMap?) {
        if (map == null) {
            status.value = StatusEnum.ERROR.status
            return
        } else {
            try {
                map.isMyLocationEnabled = true
                map.uiSettings.isMyLocationButtonEnabled = true
                status.value = StatusEnum.SUCCESS.status
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
                status.value = StatusEnum.ERROR.status
            }
        }
    }
}