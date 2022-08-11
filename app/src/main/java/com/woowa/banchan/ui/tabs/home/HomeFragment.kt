package com.woowa.banchan.ui.tabs.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentHomeBinding
import com.woowa.banchan.ui.tabs.common.BannerAdapter
import com.woowa.banchan.ui.tabs.common.CartBottomSheet
import com.woowa.banchan.ui.tabs.common.OnClickMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = requireNotNull(_binding)
    private val concatAdapter = ConcatAdapter()
    private lateinit var onClickMenu: OnClickMenu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setAdapter()
    }

    private fun setAdapter() {
        concatAdapter.addAdapter(
            BannerAdapter(
                listOf(getString(R.string.plan_banner_title)),
                true
            )
        )
        concatAdapter.addAdapter(
            PlanAdapter(
                category,
                onClick = { onClickMenu.navigateToDetail() },
                onClickCart = { CartBottomSheet().show(childFragmentManager, "cart") }
            )
        )
        binding.rvHome.adapter = concatAdapter
    }

    fun setOnClickMenu(onClickMenu: OnClickMenu) {
        this.onClickMenu = onClickMenu
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}