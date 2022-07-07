package com.example.edvoratask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewpagerAdpter(fm:FragmentManager,private val list1 : List<ridesItem>,private val station_code :Int) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return NearestFragment(list1, station_code)
            }
            1 -> {
                return UpcomingFragment(list1, station_code)
            }
            2 -> {
                return PastFragment(list1, station_code)
            }
            else -> {
                return NearestFragment(list1, station_code)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Nearest"
            }
            1 -> {
                return "Upcoming"
            }
            2 -> {
                return "Past"
            }
        }
        return super.getPageTitle(position)
    }

}