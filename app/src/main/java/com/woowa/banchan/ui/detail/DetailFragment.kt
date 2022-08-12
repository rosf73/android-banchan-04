package com.woowa.banchan.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentDetailBinding
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = requireNotNull(_binding)

    private val detailViewModel: DetailViewModel by activityViewModels()

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

        if (savedInstanceState != null) {
            binding.nsvDetailContainer.scrollY = savedInstanceState.getInt(SCROLL_Y)
        }

        //TODO: 테스트 용 코드 삭제하고 HomeFragment에서 처리
        detailViewModel.setDetailProductInfo("HBBCC", "새콤달콤 오징어무침", "국내산 오징어를 새콤달콤하게")

        initData()
        observeData()
    }

    private fun initData() {
        detailViewModel.getDetailProduct(detailViewModel.productHash)
    }

    private fun observeData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.state.collectLatest { state ->
                    initBinding(state.product)
                    initIndicators(state.product.thumbs)
                    initViewPager()

                    binding.vpDetailThumb.adapter = DetailThumbAdapter(state.product.thumbs)
                    binding.rvDetailSection.adapter =
                        DetailSectionAdapter(state.product.section)
                }
            }
        }
    }

    private fun initBinding(product: DetailProduct) {
        binding.detailViewModel = detailViewModel
        binding.product = product
    }

    private fun initIndicators(thumbs: List<String>) {
        with(binding) {
            llDetailThumb.removeAllViews()
            llDetailThumb.addView(createIndicator(true))
            for (e in 1 until thumbs.size) {
                llDetailThumb.addView(createIndicator(false))
            }
        }
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

    private fun createIndicator(selected: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        if (selected)
            imageView.setImageResource(R.drawable.indicator_round_selected)
        else
            imageView.setImageResource(R.drawable.indicator_round_unselected)
        imageView.setPadding(0, 0, 10f.toPx(), 0)

        return imageView
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
        private const val SCROLL_Y = "SCROLL_Y"
    }
}