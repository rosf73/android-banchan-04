package com.woowa.banchan.data.remote.dto

import com.squareup.moshi.Json

data class SectionResponse(
    @Json(name = "statusCode") val statusCode: Int,
    @Json(name = "body") val categories: List<CategoryDto>
)