package com.woowa.banchan.ui.screen.main.tabs.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.woowa.banchan.databinding.ItemSpinnerDropdownBinding
import com.woowa.banchan.databinding.ItemSpinnerTitleBinding

class SpinnerAdapter(
    context: Context,
    @ArrayRes filterArray: Int
) : ArrayAdapter<String>(context, 0, context.resources.getStringArray(filterArray)) {

    private lateinit var titleBinding: ItemSpinnerTitleBinding
    private lateinit var optionBinding: ItemSpinnerDropdownBinding
    private var currentPosition = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            titleBinding = ItemSpinnerTitleBinding.inflate(inflater, parent, false)
        }
        val view = convertView ?: titleBinding.root
        titleBinding.tvTitle.text = getItem(position)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            optionBinding = ItemSpinnerDropdownBinding.inflate(inflater, parent, false)
        }
        val view = convertView ?: optionBinding.root
        bindDropDownView(position)
        return view
    }

    private fun bindDropDownView(position: Int) {
        optionBinding.tvOption.text = getItem(position)
        when (position) {
            currentPosition -> {
                titleBinding.tvTitle.typeface = Typeface.DEFAULT_BOLD
                optionBinding.ivCheck.isVisible = true
            }
            else -> {
                titleBinding.tvTitle.typeface = Typeface.DEFAULT
                optionBinding.ivCheck.isGone = true
            }
        }
    }

    fun clickItem(position: Int) {
        currentPosition = position
    }
}
