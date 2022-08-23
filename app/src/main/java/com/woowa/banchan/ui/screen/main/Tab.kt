package com.woowa.banchan.ui.screen.main

enum class Tab(val title: String) {
    HOME("기획전"),
    MAIN_DISH("든든한 메인요리"),
    SOUP("뜨끈한 국물요리"),
    SIDE("정갈한 밑반찬");

    companion object {

        fun find(position: Int): String {
            val tab = values().find { it.ordinal == position }
                ?: throw IllegalAccessException("존재하지 않은 위치입니다.")
            return tab.title
        }
    }
}