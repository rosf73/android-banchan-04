package com.woowa.banchan.ui.customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowa.banchan.databinding.FragmentBottomSheetBinding
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString
import com.woowa.banchan.ui.screen.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartBottomSheet(private val product: Product) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding: FragmentBottomSheetBinding get() = requireNotNull(_binding)
    private val cartViewModel: CartViewModel by activityViewModels()

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
        binding.product = product
        binding.quantity = 1
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
                btnOrdering.text = "${quantity}개 담기"
                tvTotalPrice.text = (product!!.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
            btnPlus.setOnClickListener {
                quantity++
                btnMinus.isEnabled = true
                tvQuantity.text = quantity.toString()
                btnOrdering.text = "${quantity}개 담기"
                tvTotalPrice.text = (product!!.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
        }
        binding.btnOrdering.setOnClickListener { navigateToCart() }
    }

    fun navigateToCart() {
        cartViewModel.addCart(
            Cart(
                hash = product.detailHash,
                name = product.title,
                imageUrl = product.image,
                quantity = binding.quantity,
                price = product.sPrice,
                checked = true
            )
        )
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