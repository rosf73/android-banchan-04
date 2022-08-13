package com.woowa.banchan.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentDetailBinding
import com.woowa.banchan.domain.entity.DetailProduct
import com.woowa.banchan.ui.cart.CartFragment
import com.woowa.banchan.ui.common.OnCartClickListener
import com.woowa.banchan.ui.main.MainFragment
import com.woowa.banchan.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(), OnCartClickListener {

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

        initListener()
        initData()
        observeData()
    }

    private fun initListener() {
        binding.cartClickListener = this
    }

    private fun initData() {
        arguments?.let {
            detailViewModel.getDetailProduct(it.getString(HASH, ""))
        }
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.detailViewModel = detailViewModel
        binding.product = product

        arguments?.let {
            binding.name = it.getString(NAME, "")
            binding.description = it.getString(DESCRIPTION, "")
        }
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

    override fun navigateToCart() {
        parentFragmentManager.popBackStack("Detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .addToBackStack("Detail")
            .replace(R.id.fcv_main, CartFragment())
            .commit()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val HASH = "HASH"
        private const val NAME = "NAME"
        private const val DESCRIPTION = "DESCRIPTION"

        fun newInstance(hash: String, name: String, description: String): DetailFragment {
            val fragment = DetailFragment()

            val args = Bundle()
            args.putString(HASH, hash)
            args.putString(NAME, name)
            args.putString(DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }
}