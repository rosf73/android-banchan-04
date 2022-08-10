package com.woowa.banchan.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentDetailBinding
import com.woowa.banchan.utils.toMoneyInt
import com.woowa.banchan.utils.toMoneyString
import com.woowa.banchan.utils.toPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

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
        initView(savedInstanceState)
        setAdapter()
        setOnClickListener()
    }

    private fun initView(savedInstanceState: Bundle?) {
        initIndicators()
        initViewPager()
        initProductInfo()

        if (savedInstanceState != null) {
            binding.nsvDetailContainer.scrollY = savedInstanceState.getInt(SCROLL_Y)
        }
    }

    private fun initIndicators() {
        with(binding) {
            llDetailThumb.addView(createIndicator(true))
            for (e in 1 until testProduct.thumbs.size) {
                llDetailThumb.addView(createIndicator(false))
            }
        }
    }

    private fun createIndicator(selected: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        if (selected)
            imageView.setImageResource(R.drawable.indicator_round_selected)
        else
            imageView.setImageResource(R.drawable.indicator_round_unselected)
        imageView.setPadding(0, 0, 10f.toPx(), 0)

        return imageView
    }

    private fun initViewPager() {
        with(binding) {
            //TODO: ViewModel.position 정의 후 양방향 바인딩
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

    private fun initProductInfo() {
        with(binding) {
            product = testProduct
            description = testDescription
            quantity = 1
        }
    }

    private fun setAdapter() {
        with(binding) {
            vpDetailThumb.adapter = DetailThumbAdapter(requireContext(), testProduct.thumbs)
            rvDetailSection.adapter = DetailSectionAdapter(requireContext(), testProduct.section)
        }
    }

    private fun setOnClickListener() {
        with(binding) {
            btnMinus.setOnClickListener {
                if (quantity > 1) quantity--
                else it.isEnabled = false
            }
            btnPlus.setOnClickListener {
                quantity++
                btnMinus.isEnabled = true
            }
            btnOrdering.setOnClickListener {
                //TODO: 프래그먼트 전환
                Toast.makeText(requireContext(), "$quantity 개를 주문했습니다", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCROLL_Y, binding.nsvDetailContainer.scrollY)
    }

    companion object {
        private const val DESCRIPTION = "DESCRIPTION"
        private const val SCROLL_Y = "SCROLL_Y"

        fun newInstance(description: String): DetailFragment {
            val fragment = DetailFragment()

            val args = Bundle()
            args.putString(DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }
}