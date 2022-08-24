package com.woowa.banchan.ui.customview

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woowa.banchan.databinding.FragmentBottomSheetBinding
import com.woowa.banchan.domain.entity.Cart
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.extensions.toMoneyInt
import com.woowa.banchan.extensions.toMoneyString
import com.woowa.banchan.ui.screen.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartBottomSheet : BottomSheetDialogFragment() {

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    private fun initView() {
        binding.quantity = 1
        arguments?.let { bundle ->
            bundle.getParcelable<Product>(PRODUCT)?.let {
                binding.product = it
                setOnClickListener(it)
            }
        }
    }

    private fun setOnClickListener(product: Product) {
        with(binding) {
            btnMinus.setOnClickListener {
                if (quantity > 1)
                    quantity--
                else
                    it.isEnabled = false
                tvQuantity.text = quantity.toString()
                btnOrdering.text = "${quantity}개 담기"
                tvTotalPrice.text = (product.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
            btnPlus.setOnClickListener {
                quantity++
                btnMinus.isEnabled = true
                tvQuantity.text = quantity.toString()
                btnOrdering.text = "${quantity}개 담기"
                tvTotalPrice.text = (product.sPrice.toMoneyInt() * quantity).toMoneyString()
            }
            btnOrdering.setOnClickListener { navigateToCart(product) }
            tvCancel.setOnClickListener { cancelClickButton() }
        }
    }

    fun navigateToCart(product: Product) {
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

    companion object {
        private const val PRODUCT = "PRODUCT"

        fun newInstance(product: Product): CartBottomSheet {
            val fragment = CartBottomSheet()

            val args = Bundle()
            args.putParcelable(PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }
}
