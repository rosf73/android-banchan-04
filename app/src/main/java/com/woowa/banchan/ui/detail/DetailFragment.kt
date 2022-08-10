package com.woowa.banchan.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentDetailBinding
import com.woowa.banchan.utils.toPx

private const val DESCRIPTION = "DESCRIPTION"

class DetailFragment: Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            setIndicators(testProduct.thumbs)
            setViewPager(testProduct.thumbs)
            setProductInfo(requireArguments().getString(DESCRIPTION, ""))

            if (savedInstanceState != null) {
                binding.nsvDetailContainer.scrollY = savedInstanceState.getInt("SCROLL_Y")
            }
        }
    }

    private fun setIndicators(urlList: List<String>) {
        binding.apply {
            llDetailThumb.addView(getIndicator(true))
            for (e in 1 until urlList.size) {
                llDetailThumb.addView(getIndicator(false))
            }
        }
    }

    private fun getIndicator(selected: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        if (selected)
            imageView.setImageResource(R.drawable.indicator_round_selected)
        else
            imageView.setImageResource(R.drawable.indicator_round_unselected)
        imageView.setPadding(0, 0, 10f.toPx(requireContext()), 0)

        return imageView
    }

    private fun setViewPager(urlList: List<String>) {
        binding.apply {
            vpDetailThumb.adapter = DetailThumbAdapter(requireContext(), urlList)
            vpDetailThumb.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    llDetailThumb.children.iterator().forEach { view ->
                        (view as ImageView).setImageResource(R.drawable.indicator_round_unselected)
                    }
                    (llDetailThumb
                        .getChildAt(position) as ImageView)
                        .setImageResource(R.drawable.indicator_round_selected)
                    super.onPageSelected(position)
                }
            })
        }
    }

    private fun setProductInfo(description: String) {
        binding.apply {
            tvDetailName.text = testProduct.name
            tvDetailDescription.text = description
            tvDetailSPrice.text = testProduct.sPrice
            if (testProduct.nPrice != null) {
                tvDetailNPrice.visibility = View.VISIBLE
                tvDetailNPrice.text = testProduct.nPrice.toString()
                tvDetailNPrice.paintFlags = tvDetailNPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                tvDetailDiscountRate.visibility = View.VISIBLE
                tvDetailDiscountRate.text = "${testProduct.discountRate}%"
            }
            tvPoint.text = testProduct.point
            tvDeliveryInfo.text = testProduct.deliveryInfo
            tvDeliveryFee.text = testProduct.deliveryFee
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun newInstance(description: String): DetailFragment {
        val fragment = DetailFragment()

        // Supply index input as an argument.
        val args = Bundle()
        args.putString(DESCRIPTION, description)
        fragment.arguments = args
        return fragment
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCROLL_Y", binding.nsvDetailContainer.scrollY)
    }
}

data class TestProduct(
    val name: String,
    val thumbs: List<String>,
    val sPrice: String,
    val nPrice: String?,
    val point: String,
    val deliveryInfo: String,
    val deliveryFee: String
) {

    val discountRate: Int
        get() = if (nPrice == null) 0
                else {
                    val tempS = sPrice.replace(Regex(",|원"), "").toFloat()
                    val tempN = nPrice.replace(Regex(",|원"), "").toFloat()
                    ((tempN - tempS) / tempN * 100).toInt()
                }
}

val testProduct = TestProduct(
    name = "오리 주물럭_반조리",
    thumbs = listOf(
        "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_T.jpg",
        "http://public.codesquad.kr/jk/storeapp/data/main/1155_ZIP_P_0081_S.jpg"
    ),
    sPrice = "12,640원",
    nPrice = "15,800원",
    point = "126원",
    deliveryInfo = "서울 경기 새벽 배송, 전국 택배 배송",
    deliveryFee = "2,500원 (40,000원 이상 구매 시 무료)"
)