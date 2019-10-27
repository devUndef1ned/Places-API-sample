package com.devundefined.placesapisample.presentation.placelist

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.devundefined.placesapisample.PlacesApiApplication
import com.devundefined.placesapisample.R
import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.Place
import com.devundefined.placesapisample.switchVisibility
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class PlaceListFragment : MvpAppCompatFragment(R.layout.fragment_place_list), PlaceListView {

    @InjectPresenter
    lateinit var presenter: PlaceListPresenter

    private val recyclerView: RecyclerView
        get() = view!!.findViewById(R.id.recycler)
    private val loader: ProgressBar
        get() = view!!.findViewById(R.id.progress)
    private val contentContainer: View
        get() = view!!.findViewById(R.id.content_container)
    private val errorContainer: View
        get() = view!!.findViewById(R.id.error_container)
    private val requestLocation: View
        get() = view!!.findViewById(R.id.reload_places)

    @ProvidePresenter
    fun providePresenter() = PlaceListPresenter(
        arguments!!.getSerializable(KEY_ARG_USER_LOCATION) as Location,
        PlacesApiApplication.INSTANCE.appComponent.placesLoadService()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocation.setOnClickListener { presenter.requestPlaces() }
    }

    override fun showLoading() {
        loader.switchVisibility(true)
        contentContainer.switchVisibility(false)
        errorContainer.switchVisibility(false)
    }

    override fun showError(e: Throwable) {
        loader.switchVisibility(false)
        contentContainer.switchVisibility(false)
        errorContainer.switchVisibility(true)
    }

    override fun showPlaceList(placeList: List<Place>) {
        loader.switchVisibility(false)
        contentContainer.switchVisibility(true)
        errorContainer.switchVisibility(false)
    }

    companion object {
        private const val KEY_ARG_USER_LOCATION = "key_arg_user_location"
        fun newInstance(userLocation: Location) = PlaceListFragment().apply {
            arguments = Bundle().apply {
                putSerializable(KEY_ARG_USER_LOCATION, userLocation)
            }
        }
    }
}