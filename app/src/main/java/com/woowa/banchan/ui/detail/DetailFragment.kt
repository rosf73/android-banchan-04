package com.woowa.banchan.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentDetailBinding
import com.woowa.banchan.utils.toPx

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

        val testList = listOf(
            "https://swebtoon-phinf.pstatic.net/20211115_192/1636963468245dquJG_PNG/thumbnail.jpg",
            "https://media.istockphoto.com/photos/modern-black-picture-or-square-photo-frame-isolated-picture-id1372401945?b=1&k=20&m=1372401945&s=170667a&w=0&h=0hMSes57k6UF54gCiE_9DE9zEcgXOYwXqBR8PFdrqok="
        )
        setIndicators(testList)
        setViewPager(testList)
    }

    private fun setIndicators(urlList: List<String>) {
        binding.apply {
            llDetailThumb.addView(getIndicator(true))
            for (e in 1 until urlList.size) {
                llDetailThumb.addView(getIndicator(false))
            }
        }
    }

    private fun setViewPager(urlList: List<String>) {
        binding.apply {
            vpDetailThumb.adapter = DetailThumbAdapter(lifecycleScope, urlList)
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

    private fun getIndicator(selected: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        if (selected)
            imageView.setImageResource(R.drawable.indicator_round_selected)
        else
            imageView.setImageResource(R.drawable.indicator_round_unselected)
        imageView.setPadding(0, 0, 10f.toPx(requireContext()), 0)

        return imageView
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}