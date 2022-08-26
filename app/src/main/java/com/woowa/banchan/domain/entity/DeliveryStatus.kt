package com.woowa.banchan.domain.entity

enum class DeliveryStatus(val status: String) {
    START("START"),
    DONE("DONE");

    companion object {
        private const val NOT_FOUND_DELIVERY_STATUS = "존재하지 않는 배달 상태입니다."

        fun of(status: String): DeliveryStatus {
            return values().find { it.status == status } ?: throw Exception(
                NOT_FOUND_DELIVERY_STATUS
            )
        }
    }
}
