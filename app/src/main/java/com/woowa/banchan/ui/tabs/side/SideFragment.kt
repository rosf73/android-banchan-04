package com.woowa.banchan.ui.tabs.side

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowa.banchan.databinding.FragmentSideBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SideFragment : Fragment() {

    private var _binding: FragmentSideBinding? = null
    private val binding: FragmentSideBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}