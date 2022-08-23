package com.woowa.banchan.ui.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.woowa.banchan.databinding.FragmentOrderDetailBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding: FragmentOrderDetailBinding get() = requireNotNull(_binding)
    private val orderDetailViewModel: OrderDetailViewModel by viewModels()
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
        binding.rvOrder.adapter = orderDetailAdapter
        binding.viewModel = orderDetailViewModel

        arguments?.getLong(ORDER_ID)?.let {
            orderDetailViewModel.getOrderLineItem(it)
            binding.orderId = it
        }
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                orderDetailViewModel.state.collect {
                    if (it.orderLineItemList.isNotEmpty()) {
                        orderDetailAdapter.submitHeaderAndOrderList(it.orderLineItemList)
                    }
                }
            }

            launch {
                orderDetailViewModel.refreshEvent.collectLatest {
                    orderDetailAdapter.notifyItemChanged(0)
                }
            }
        }
    }

    private fun onCompleteOrder() {
        orderDetailViewModel.modifyOrder()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val ORDER_ID = "ORDER_ID"

        fun newInstance(id: Long): OrderDetailFragment {
            val fragment = OrderDetailFragment()

            val args = Bundle()
            args.putLong(ORDER_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}