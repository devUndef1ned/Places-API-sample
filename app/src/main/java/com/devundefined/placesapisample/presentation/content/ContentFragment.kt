package com.devundefined.placesapisample.presentation.content

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import com.devundefined.placesapisample.PlacesApiApplication
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.Location
import com.google.android.material.tabs.TabLayout
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class ContentFragment : MvpAppCompatFragment(R.layout.fragment_content), ContentView {

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
    private val tabLayout: TabLayout
        get() = view!!.findViewById(R.id.tabs)
    private val viewPager: ViewPager
        get() = view!!.findViewById(R.id.view_pager)

    @ProvidePresenter
    fun providePresenter() = PlacesApiApplication.INSTANCE.appComponent.pagerPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocation.setOnClickListener { presenter.requestUserLocation() }
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun showLoading() {
        loader.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
    }

    override fun showUserLocation(userLocation: Location) {
        viewPager.adapter = CategoryPageAdapter(childFragmentManager, userLocation)
        loader.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
    }

    override fun showError(e: Throwable) {
        android.util.Log.w("ContentFragment", e.message, e)
        loader.visibility = View.GONE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
    }
}