package uz.gita.weatherappinkotlinmvvm.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import uz.gita.weatherappinkotlinmvvm.data.source.local.SharedPref


//Latitude va Longtitude ni olish
class CurrentLocation private constructor(private val context: Context) {

    private val sharedPref = SharedPref.getInstance()

    companion object {
         lateinit var location: CurrentLocation

        fun getInstance(context: Context): CurrentLocation {
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
                context as Activity,
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