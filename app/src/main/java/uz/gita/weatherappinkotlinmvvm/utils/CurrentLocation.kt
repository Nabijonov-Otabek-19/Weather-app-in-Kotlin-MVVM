package uz.gita.weatherappinkotlinmvvm.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import uz.gita.weatherappinkotlinmvvm.data.source.local.SharedPref


//Latitude va Longtitude ni olish
class CurrentLocation private constructor(private val context: Activity) {

    private val sharedPref = SharedPref.getInstance(context)

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var location: CurrentLocation

        fun getInstance(context: Activity): CurrentLocation {
            if (!(::location.isInitialized)) {
                location = CurrentLocation(context)
            }
            return location
        }
    }

    private val fusedLocation = LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
            return
        }

        val location = fusedLocation.lastLocation

        location.addOnSuccessListener {
            if (it != null) {
                sharedPref.location = "${it.latitude},${it.longitude}"
            }
        }
    }
}