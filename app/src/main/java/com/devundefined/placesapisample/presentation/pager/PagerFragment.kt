package com.devundefined.placesapisample.presentation.pager

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

    override fun showLoading() {
        loader.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
    }

    override fun showUserLocationFragment(userLocation: UserLocation) {
        loader.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
    }
}