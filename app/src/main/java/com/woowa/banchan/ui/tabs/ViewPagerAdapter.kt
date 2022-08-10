package com.woowa.banchan.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woowa.banchan.ui.tabs.home.HomeFragment
import com.woowa.banchan.ui.tabs.maindish.MainDishFragment
import com.woowa.banchan.ui.tabs.side.SideFragment
import com.woowa.banchan.ui.tabs.soup.SoupFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = SIZE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MainDishFragment()
            2 -> SoupFragment()
            3 -> SideFragment()
            else -> HomeFragment()
        }
    }

    companion object {
        const val SIZE = 4
    }
}