package ir.hirkancorp.presenter.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import ir.hirkancorp.presenter.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.osmdroid.bonuspack.location.GeocoderNominatim
import java.io.IOException
import java.util.Locale
import ir.hirkancorp.core.LoggerUtil as LoggerUtil1

object LocationUtil : KoinComponent {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var geocoder: GeocoderNominatim
    private val scope = CoroutineScope(Dispatchers.IO)

    private val context: Context by inject()

    init {
        initfusedLocationProviderClient()
    }

    private fun initfusedLocationProviderClient() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        geocoder = GeocoderNominatim(Locale("fa"), "ir.hirkancorp.user")
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        context: Context,
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception?) -> Unit,
        priority: Boolean = true
    ) {

        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
        else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        if (areLocationPermissionsGranted(context)) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                it?.let {
                    onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                } ?: run {
                    fusedLocationProviderClient.getCurrentLocation(
                        accuracy, CancellationTokenSource().token,
                    ).addOnSuccessListener { location ->
                        location?.let {
                            onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                        } ?: run {
                            onGetCurrentLocationFailed(null)
                        }
                    }.addOnFailureListener { exception ->
                        onGetCurrentLocationFailed(exception)
                    }
                }
            }.addOnFailureListener { exception ->
                onGetCurrentLocationFailed(exception)
            }
        }
    }

    fun getAddress(
        location: Pair<Double, Double>,
        maxResult: Int = 1,
        resultCallBack: (String?) -> Unit
    ) {
        scope.async {
            try {
                val addresses = geocoder.getFromLocation(location.first, location.second, maxResult)
                val bundle = addresses[0].extras
                val address = bundle.getString("display_name")
                resultCallBack(address)
                LoggerUtil1.logE(::getAddress.name, address ?: "")
            } catch (exception: IOException) {
                exception.printStackTrace()
                resultCallBack(null)
                LoggerUtil1.logE(::getAddress.name, exception.message ?: context.getString(R.string.all_unknown_error))
            }
            this.cancel()
        }
    }

    private fun areLocationPermissionsGranted(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }
}