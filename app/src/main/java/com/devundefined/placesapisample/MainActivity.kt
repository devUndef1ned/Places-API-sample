package com.devundefined.placesapisample

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val contentContainer: View
        get() = findViewById(R.id.content_container)
    private val permissionErrorContainer: View
        get() = findViewById(R.id.permission_error_container)
    private val requestPermissionButton: Button
        get() = findViewById(R.id.request_permission)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionButton.setOnClickListener { requestLocationPermission() }
    }

    override fun onStart() {
        super.onStart()
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                LOCATION_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            showContent()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(LOCATION_PERMISSION),
            PERMISSION_REQUEST_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_ID -> handleLocationPermissionResults(grantResults[0])
        }
    }

    private fun handleLocationPermissionResults(result: Int) {
        if (result == PackageManager.PERMISSION_GRANTED) {
            showContent()
        } else {
            showPermissionError()
        }
    }

    fun showPermissionError() {
        permissionErrorContainer.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
    }
    fun showContent() {
        permissionErrorContainer.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
    }


    companion object {
        private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_REQUEST_ID = 1
    }

}
