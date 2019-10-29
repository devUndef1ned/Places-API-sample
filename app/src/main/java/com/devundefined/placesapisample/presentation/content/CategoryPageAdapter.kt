package com.devundefined.placesapisample.presentation.content

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.devundefined.placesapisample.domain.Location
import com.devundefined.placesapisample.domain.PlaceCategory
import com.devundefined.placesapisample.presentation.placelist.PlaceListFragment

class CategoryPageAdapter(
    fragmentManager: FragmentManager,
    private val userLocation: Location
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val categories = PlaceCategory.values().toList()

    override fun getItem(position: Int): Fragment = PlaceListFragment.newInstance(userLocation, categories[position])

    override fun getCount() = categories.size

    override fun getPageTitle(position: Int) = categories[position].value
}