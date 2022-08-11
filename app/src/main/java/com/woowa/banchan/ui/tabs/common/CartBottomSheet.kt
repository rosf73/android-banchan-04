package com.woowa.banchan.ui.tabs.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowa.banchan.databinding.FragmentBottomSheetBinding
import com.woowa.banchan.ui.detail.quantity
import com.woowa.banchan.ui.detail.testProduct
import com.woowa.banchan.utils.toMoneyInt
import com.woowa.banchan.utils.toMoneyString

class CartBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding: FragmentBottomSheetBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.fragment = this@CartBottomSheet
        setOnClickListener()
    }

    private fun setOnClickListener() {
        with(binding) {
            btnMinus.setOnClickListener {
                if (quantity > 1)
                    quantity--
                else
                    it.isEnabled = false
                tvQuantity.text = quantity.toString()
                tvTotalPrice.text = (testProduct.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
            btnPlus.setOnClickListener {
                quantity++
                btnMinus.isEnabled = true
                tvQuantity.text = quantity.toString()
                tvTotalPrice.text = (testProduct.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
        }
    }

    fun navigateToCart() {
        dismiss()
        val dialog = CartDialog()
        dialog.show(parentFragmentManager, dialog.tag)
    }

    fun cancelClickButton() {
        dismiss()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}