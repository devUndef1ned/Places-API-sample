package com.devundefined.placesapisample.presentation.content

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.devundefined.placesapisample.PlacesApiApplication
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.presentation.placelist.PlaceListFragment
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

    @ProvidePresenter
    fun providePresenter() = PlacesApiApplication.INSTANCE.appComponent.pagerPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocation.setOnClickListener { presenter.requestUserLocation() }
    }

    override fun showLoading() {
        loader.visibility = View.VISIBLE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.GONE
    }

    override fun showUserLocation(userLocation: Location) {
        loader.visibility = View.GONE
        contentContainer.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        val fragment = childFragmentManager.findFragmentByTag(TAG_PLACE_LIST_FRAGMENT)
        if (fragment == null) {
            val placesFragment = PlaceListFragment.newInstance(userLocation)
            childFragmentManager.beginTransaction()
                .add(R.id.content_container, placesFragment, TAG_PLACE_LIST_FRAGMENT)
                .commit()
        }
    }

    override fun showError(e: Throwable) {
        android.util.Log.w("ContentFragment", e.message, e)
        loader.visibility = View.GONE
        contentContainer.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
    }

    companion object {
        private const val TAG_PLACE_LIST_FRAGMENT = "tag_place_list_fragment"
    }
}