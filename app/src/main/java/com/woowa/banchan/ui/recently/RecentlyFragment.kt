package com.woowa.banchan.ui.recently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.woowa.banchan.databinding.FragmentRecentlyBinding
import com.woowa.banchan.ui.OnBackClickListener

class RecentlyFragment: Fragment() {

    private var _binding: FragmentRecentlyBinding? = null
    private val binding: FragmentRecentlyBinding get() = requireNotNull(_binding)

    private val recentlyViewModel: RecentlyViewModel by viewModels()

    private lateinit var recentlyAdapter: RecentlyAdapter

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
        binding.rvRecently.adapter = RecentlyAdapter(testRecentlyList, {}, {})
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }
}