package com.woowa.banchan.ui.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.woowa.banchan.databinding.FragmentOrderDetailBinding
import com.woowa.banchan.domain.entity.OrderDetailSection.Order
import com.woowa.banchan.domain.entity.OrderDetailSection.OrderLineItem
import com.woowa.banchan.ui.OnBackClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding: FragmentOrderDetailBinding get() = requireNotNull(_binding)
    private val orderDetailAdapter by lazy {
        OrderDetailAdapter(onComplete = this::onCompleteOrder)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.listener = activity as OnBackClickListener
    }

    private fun observeData() {
        binding.rvOrder.adapter = orderDetailAdapter
        val orderLineItems = listOf<OrderLineItem>(
            OrderLineItem(
                name = "오징어 무침",
                imageUrl = "",
                quantity = 4,
                price = "3,200원"
            ),
            OrderLineItem(
                name = "오징어 무침",
                imageUrl = "",
                quantity = 3,
                price = "7,200원"
            ),
            OrderLineItem(
                name = "오징어 무침",
                imageUrl = "",
                quantity = 2,
                price = "1,200원"
            )
        )
        orderDetailAdapter.submitHeaderAndOrderList(
            mapOf<Order, List<OrderLineItem>>(
                Order(
                    id = 1L,
                    orderedAt = System.currentTimeMillis(),
                    status = "Start",
                    count = 3
                ) to orderLineItems
            )
        )
    }

    private fun onCompleteOrder() {
        // TODO 주문 완료시간 됬었을 때 처리
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}