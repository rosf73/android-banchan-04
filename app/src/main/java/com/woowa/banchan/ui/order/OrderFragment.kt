package com.woowa.banchan.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.woowa.banchan.databinding.FragmentOrderBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.orderdetail.OrderDetailViewModel

class OrderFragment: Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = requireNotNull(_binding)

    private val orderViewModel: OrderDetailViewModel by viewModels()

    private val orderListAdapter by lazy {
        OrderListAdapter(
            onClickItem = {

            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
        observeData()
    }

    private fun initListener() {
        binding.listener = activity as OnBackClickListener
    }

    private fun initView() {
        binding.rvOrderList.adapter = orderListAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}