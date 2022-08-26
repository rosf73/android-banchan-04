package com.woowa.banchan.ui.screen.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woowa.banchan.ui.screen.main.tabs.maindish.MainDishFragment
import com.woowa.banchan.ui.screen.main.tabs.plan.PlanFragment
import com.woowa.banchan.ui.screen.main.tabs.side.SideFragment
import com.woowa.banchan.ui.screen.main.tabs.soup.SoupFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = SIZE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Tab.HOME.ordinal -> PlanFragment()
            Tab.MAIN_DISH.ordinal -> MainDishFragment()
            Tab.SOUP.ordinal -> SoupFragment()
            Tab.SIDE.ordinal -> SideFragment()
            else -> PlanFragment()
        }
    }

    companion object {
        const val SIZE = 4
    }
}
