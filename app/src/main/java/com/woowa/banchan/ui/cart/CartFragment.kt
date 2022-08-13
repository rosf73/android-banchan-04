package com.woowa.banchan.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowa.banchan.databinding.FragmentCartBinding
import com.woowa.banchan.ui.common.OnBackClickListener
import com.woowa.banchan.ui.main.MainFragment

class CartFragment: Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listener = activity as OnBackClickListener
    }
}