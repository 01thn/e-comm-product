package by.ecomm.ecommproduct.dto

import java.time.ZonedDateTime

data class CategoryResponseDto(
    val id: Long,
    val name: String,
    var products: MutableList<ProductResponseDto>?,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
