package com.woowa.banchan.ui.customview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.DialogFragment
import com.woowa.banchan.databinding.FragmentLoadingBinding
import com.woowa.banchan.ui.extensions.currentWindowMetricsPointCompat

class LoadingFragment : DialogFragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding: FragmentLoadingBinding get() = requireNotNull(_binding)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                requireActivity().finishAffinity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = windowManager.currentWindowMetricsPointCompat()
        val deviceWidth = size.x
        val deviceHeight = size.y
        if (deviceHeight > deviceWidth) {
            params?.width = (deviceWidth * 0.5).toInt()
        } else {
            params?.width = (deviceWidth * 0.2).toInt()
        }
        params?.height = deviceHeight
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        isCancelable = false
        binding.ivLoading.startAnimation(
            RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 800L
                repeatCount = Animation.INFINITE
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.ivLoading.clearAnimation()
    }
}
