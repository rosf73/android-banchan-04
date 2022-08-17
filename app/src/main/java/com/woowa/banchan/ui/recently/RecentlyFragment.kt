package com.woowa.banchan.ui.recently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentRecentlyBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnCartClickListener
import com.woowa.banchan.ui.OnDetailClickListener
import com.woowa.banchan.ui.detail.DetailFragment

class RecentlyFragment: Fragment(), OnDetailClickListener, OnCartClickListener {

    private var _binding: FragmentRecentlyBinding? = null
    private val binding: FragmentRecentlyBinding get() = requireNotNull(_binding)

    private val recentlyViewModel: RecentlyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        binding.rvRecently.adapter = RecentlyAdapter(
            testRecentlyList,
            onClick = {
                navigateToDetail(it.detailHash, it.title, it.description)
            },
            onClickCart = {
                navigateToCart()
            }
        )
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Recently", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .addToBackStack("Recently")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToCart() {

    }
}