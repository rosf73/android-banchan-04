package com.woowa.banchan.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentOrderBinding
import com.woowa.banchan.ui.OnBackClickListener
import com.woowa.banchan.ui.OnOrderDetailClickListener
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.orderdetail.OrderDetailFragment

class OrderFragment: Fragment(), OnOrderDetailClickListener {

    private var _binding: FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding get() = requireNotNull(_binding)

    private val orderViewModel: OrderViewModel by activityViewModels()

    private val orderListAdapter by lazy {
        OrderListAdapter(
            onClickItem = {
                navigateToOrderDetail()
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
        orderViewModel.getAllOrder()
        viewLifecycleOwner.repeatOnLifecycle {
            orderViewModel.state.collect {
                if (it.orderInfoList.isNotEmpty()) {
                    orderListAdapter.submitList(it.orderInfoList)
                }
            }
        }
    }

    override fun navigateToOrderDetail() {
        parentFragmentManager.popBackStack("Order", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
            .addToBackStack("Order")
            .add(R.id.fcv_main, OrderDetailFragment())
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}