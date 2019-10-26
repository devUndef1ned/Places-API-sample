package com.devundefined.placesapisample.presentation.pager

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.UserLocation
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class PagerFragment : MvpAppCompatFragment(R.layout.fragment_pager), PagerView {

    @InjectPresenter
    lateinit var presenter: PagerPresenter

    private val loader: ProgressBar
        get() = view!!.findViewById(R.id.progress)
    private val contentContainer: View
        get() = view!!.findViewById(R.id.content_container)
    private val errorContainer: View
        get() = view!!.findViewById(R.id.error_container)
    private val requestLocation: View
        get() = view!!.findViewById(R.id.request_location)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocation.setOnClickListener { presenter.requestUserLocation() }
    }

    override fun showLoading() {
        loader.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
    }

    override fun showUserLocationFragment(userLocation: UserLocation) {
        loader.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
    }

    override fun showError(e: Throwable) {
        loader.visibility = View.GONE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
    }
}