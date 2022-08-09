package com.woowa.banchan.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentMainBinding
import com.woowa.banchan.ui.tabs.ViewPagerAdapter

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titles = arrayOf(
            getString(R.string.home),
            getString(R.string.main_dish),
            getString(R.string.soup),
            getString(R.string.side)
        )
        binding.apply {
            vpOrdering.adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
            TabLayoutMediator(tlOrdering, vpOrdering) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}