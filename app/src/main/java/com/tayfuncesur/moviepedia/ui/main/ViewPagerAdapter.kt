package com.tayfuncesur.moviepedia.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.tayfuncesur.moviepedia.R
import com.tayfuncesur.moviepedia.ui.nowPlaying.NowPlayingFragment
import com.tayfuncesur.moviepedia.ui.topRated.TopRatedFragment
import com.tayfuncesur.moviepedia.ui.upcoming.UpcomingFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, var context: Context) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> TopRatedFragment.newInstance()
            1 -> UpcomingFragment.newInstance()
            2 -> NowPlayingFragment.newInstance()
            else -> TopRatedFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.first_tab)
            1 -> context.getString(R.string.second_tab)
            2 -> context.getString(R.string.third_tab)
            else -> context.getString(R.string.first_tab)
        }
    }
}