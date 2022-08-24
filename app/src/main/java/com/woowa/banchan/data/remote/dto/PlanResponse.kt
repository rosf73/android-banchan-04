package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class PlanResponse(
    @Json(name = "statusCode") val statusCode: Int,
    @Json(name = "body") val categories: List<CategoryDto>
)
