package com.woowa.banchan.ui.screen.recently

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentRecentlyBinding
import com.woowa.banchan.domain.entity.Product
import com.woowa.banchan.domain.entity.toProduct
import com.woowa.banchan.ui.customview.CartBottomSheet
import com.woowa.banchan.ui.extensions.repeatOnLifecycle
import com.woowa.banchan.ui.extensions.toVisibility
import com.woowa.banchan.ui.navigator.OnBackClickListener
import com.woowa.banchan.ui.navigator.OnDetailClickListener
import com.woowa.banchan.ui.navigator.OnItemCartClickListener
import com.woowa.banchan.ui.screen.detail.DetailFragment
import com.woowa.banchan.ui.screen.main.tabs.ProductUiEvent
import kotlinx.coroutines.launch
import java.util.Calendar

class RecentlyFragment : Fragment(), OnDetailClickListener, OnItemCartClickListener {

    private var _binding: FragmentRecentlyBinding? = null
    private val binding: FragmentRecentlyBinding get() = requireNotNull(_binding)

    private val recentlyViewModel: RecentlyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        observeData()
    }

    private fun initView() {
        binding.viewModel = recentlyViewModel
    }

    private fun observeData() {
        viewLifecycleOwner.repeatOnLifecycle {
            launch {
                recentlyViewModel.state.collect { state ->
                    val isNotEmpty = state.recentlyList.isNotEmpty()
                    binding.rvRecently.visibility = isNotEmpty.toVisibility()
                    binding.llEmpty.visibility = (!isNotEmpty).toVisibility()
                    binding.ivLockandlock.visibility = (!isNotEmpty).toVisibility()

                    if (isNotEmpty)
                        binding.rvRecently.adapter = RecentlyAdapter(
                            state.recentlyList,
                            onClick = { recentlyViewModel.navigateToDetail(it.copy(viewedAt = Calendar.getInstance().time.time)) },
                            onClickCart = { recentlyViewModel.navigateToCart(it) }
                        )
                    else
                        binding.ivLockandlock.startAnimation(
                            AnimationUtils.loadAnimation(
                                requireContext(),
                                R.anim.translate_infinity
                            )
                        )
                }
            }

            launch {
                recentlyViewModel.eventFlow.collect {
                    when (it) {
                        is ProductUiEvent.ShowToast -> showToastMessage(it.message)
                        is ProductUiEvent.NavigateToDetail -> navigateToDetail(
                            it.data.hash,
                            it.data.name,
                            it.data.description
                        )
                        is ProductUiEvent.NavigateToCart -> navigateToCart(it.data.toProduct())
                        is ProductUiEvent.NavigateToBack -> (activity as OnBackClickListener).navigateToBack()
                    }
                }
            }
        }
    }

    private fun showToastMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateToDetail(hash: String, name: String, description: String) {
        parentFragmentManager.popBackStack("Recently", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .addToBackStack("Recently")
            .add(R.id.fcv_main, DetailFragment.newInstance(hash, name, description))
            .commit()
    }

    override fun navigateToCart(product: Product) {
        CartBottomSheet.newInstance(product).show(childFragmentManager, "cart")
    }
}
