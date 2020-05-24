package com.renderforest.weatherapp.base.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import com.renderforest.weatherapp.BuildConfig
import com.renderforest.weatherapp.R
import com.renderforest.weatherapp.utils.Utils
import dagger.android.AndroidInjection
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.location.config.LocationParams
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider
import kotlinx.android.synthetic.main.activity_base.*
import org.greenrobot.eventbus.EventBus
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

abstract class BaseActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        ResultCallback<LocationSettingsResult>, EasyPermissions.PermissionCallbacks {

    val locationPerms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var TAG = BaseActivity::class.java.simpleName
    var navController: NavController? = null
    private var statusBarColor = android.R.color.transparent
    private var errorShown = false

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationClient: FusedLocationProviderClient? = null

    private var GPS_REQUEST_CODE = 1001

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setUpGClient()
        setContentView(R.layout.activity_base)
        LayoutInflater.from(this).inflate(getLayoutResId(), container_layout, true)
        applyStatusBarColor()

        TAG = javaClass.simpleName
        if (findViewById<View>(R.id.nav_host_fragment) != null) {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        } else {
            Log.i("INFO", "page does not have nav controller")
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    @Synchronized
    open fun setUpGClient() {
        mGoogleApiClient?.let { return }

        mLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient?.connect()
    }

    private fun applyStatusBarColor() {
        val window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, getStatusBarColor())
        window.navigationBarColor = ContextCompat.getColor(this, getStatusBarColor())
        if (useLightStatusBar()) {
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun closeKeyboard() {
        val view = currentFocus
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun openKeyboard(view: View, millis: Long) {
        Handler().postDelayed(
                {
                    val inputMethodManager = (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    inputMethodManager.toggleSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
                    view.requestFocus()
                }, millis
        )
    }

    open fun useLightStatusBar(): Boolean {
        return true
    }

    open fun getStatusBarColor(): Int {
        return statusBarColor
    }

    fun setStatusBarColor(colorRes: Int) {
        statusBarColor = colorRes
        applyStatusBarColor()
    }


    fun showMessageDialog(text: String) {
        if (errorShown || text.trim().isBlank()) {
            return
        }
        errorShown = true
        AlertDialog.Builder(this)
                .setMessage(text)
                .setPositiveButton(
                        android.R.string.ok
                ) { dialogInterface, _ ->
                    errorShown = false
                    dialogInterface.dismiss()
                }.show()
    }

    fun popBackStack(destId: Int = -1, inclusive: Boolean = true) {
        if (destId != -1) {
            navController?.popBackStack(destId, inclusive)
        } else {
            navController?.popBackStack()
        }
    }

    fun navigate(@IdRes resId: Int, @Nullable args: Bundle?) {
        if (navController == null) {
            if (BuildConfig.DEBUG) {
                toast("Can not navigate NavController is NULL")
            }
            return
        }
        try {
            navController?.navigate(resId, args)
        } catch (e: IllegalArgumentException) {
            if (BuildConfig.DEBUG) {
                toast(e.message ?: "Navigation error")
            }
            e.printStackTrace()
        }
    }

    fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onResult(locationSettingsResult: LocationSettingsResult) {
        val status = locationSettingsResult.status
        when (status.statusCode) {
            LocationSettingsStatusCodes.SUCCESS -> {
                try {
                    status.startResolutionForResult(this, GPS_REQUEST_CODE)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                status.startResolutionForResult(this, GPS_REQUEST_CODE)
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_REQUEST_CODE -> {
                if (resultCode == RESULT_OK) {
                    requestLocation()
                }
            }
        }
    }

    fun lasKnownLocation(): Location? {
        return SmartLocation.with(this)
                .location(LocationGooglePlayServicesProvider())
                .config(LocationParams.NAVIGATION)
                .oneFix().lastLocation
    }

    fun requestLocation() {
        SmartLocation.with(this)
                .location(LocationGooglePlayServicesProvider())
                .config(LocationParams.NAVIGATION)
                .oneFix()
                .start { location ->
                    EventBus.getDefault().post(location)
                    println("location: lat:${location.latitude} lng:${location.longitude}")
                }
    }

    fun getLocation() {
        if (EasyPermissions.hasPermissions(this, *locationPerms)) {
            if (Utils.isGPSAvailable()) {
                requestLocation()
            } else {
                checkGPSSettings()
            }
        } else {
            EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(this, 1001, *locationPerms)
                            .setPositiveButtonText(android.R.string.ok)
                            .setNegativeButtonText(android.R.string.cancel)
                            .build()
            )
        }
    }

    @AfterPermissionGranted(1001)
    fun locationPermissionGranted() {
        if (Utils.isGPSAvailable()) {
            requestLocation()
        } else {
            checkGPSSettings()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        if (requestCode == 1001) {
//            if (Utils.isGPSAvailable()) {
//                requestLocation()
//            } else {
//                checkGPSSettings()
//            }
//        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    open fun checkGPSSettings() {
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000
                    fastestInterval = 1000
                })
        builder.setAlwaysShow(true)
        val result = LocationServices.SettingsApi.checkLocationSettings(
                mGoogleApiClient,
                builder.build()
        )
        result.setResultCallback(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}