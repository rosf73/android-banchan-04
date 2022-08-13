package com.woowa.banchan.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ViewAppBarBinding

class BanchanAppbar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding: ViewAppBarBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_app_bar, this, true)

    private val navigationIcon = binding.ivNavigationIcon
    private val title = binding.tvTitle
    private val actionFirst = binding.ivActionFirst
    private val actionSecond = binding.ivActionSecond

    init {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BanchanAppBar, 0, 0).apply {
            setTitle(getString(R.styleable.BanchanAppBar_title))
            setNavigationIcon(getResourceId(R.styleable.BanchanAppBar_navigation_icon, 0))
            setActionFirst(getResourceId(R.styleable.BanchanAppBar_action_first, 0))
            setActionSecond(getResourceId(R.styleable.BanchanAppBar_action_second, 0))
        }
    }

    private fun setActionSecond(resourceId: Int) {
        if (resourceId != 0) this.actionSecond.setImageResource(resourceId)
        else actionSecond.isVisible = isGone
    }

    private fun setActionFirst(resourceId: Int) {
        if (resourceId != 0) this.actionFirst.setImageResource(resourceId)
        else actionFirst.isVisible = isGone
    }

    private fun setNavigationIcon(resourceId: Int) {
        if (resourceId != 0) this.navigationIcon.setImageResource(resourceId)
    }

    private fun setTitle(title: String?) {
        if (title != null) this.title.text = title
    }

    fun onNavigationIconClick(onClick: () -> Unit) {
        this.navigationIcon.setOnClickListener {
            onClick()
        }
    }

    fun onActionFirstClick(onClick: () -> Unit) {
        this.actionFirst.setOnClickListener {
            onClick()
        }
    }

    fun onActionSecondClick(onClick: () -> Unit) {
        this.actionSecond.setOnClickListener {
            onClick()
        }
    }

}