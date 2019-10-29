package com.devundefined.placesapisample.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.presentation.content.ContentFragment
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), MainView, PermissionsHandler {

    private val contentContainer: View
        get() = findViewById(R.id.content_container)
    private val permissionErrorContainer: View
        get() = findViewById(R.id.permission_error_container)
    private val requestPermissionButton: Button
        get() = findViewById(R.id.request_permission)

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f
        requestPermissionButton.setOnClickListener { requestLocationPermission() }
    }

    override fun onStart() {
        super.onStart()
        presenter.setPermissionHandler(this)
    }

    override fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(LOCATION_PERMISSION),
            PERMISSION_REQUEST_ID
        )
    }

    override fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            LOCATION_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_ID ->
                presenter.handlePermissionsResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    override fun showPermissionError() {
        permissionErrorContainer.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
    }

    override fun showContent() {
        permissionErrorContainer.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
        checkFragmentAndAddIfThereIsNotAny()
    }

    private fun checkFragmentAndAddIfThereIsNotAny() {
        val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as ContentFragment?
        if (fragment == null) {
            supportFragmentManager.beginTransaction().add(contentContainer.id, ContentFragment(), FRAGMENT_TAG).commit()
        }
    }


    companion object {
        private const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        private const val PERMISSION_REQUEST_ID = 1
        private const val FRAGMENT_TAG = "fragment_tag"
    }
}
