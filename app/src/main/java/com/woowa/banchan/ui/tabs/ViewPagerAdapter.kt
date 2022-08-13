package com.woowa.banchan.ui.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woowa.banchan.ui.main.Tab
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
            Tab.HOME.ordinal -> HomeFragment()
            Tab.MAIN_DISH.ordinal -> MainDishFragment()
            Tab.SOUP.ordinal -> SoupFragment()
            Tab.SIDE.ordinal -> SideFragment()
            else -> HomeFragment()
        }
    }

    companion object {
        const val SIZE = 4
    }
}