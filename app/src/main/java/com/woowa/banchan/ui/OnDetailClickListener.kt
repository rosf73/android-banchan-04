package com.woowa.banchan.ui

interface OnDetailClickListener {

    fun navigateToDetail(hash: String, name: String, description: String)
}