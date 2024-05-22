package by.ecomm.ecommproduct.dto

import by.ecomm.ecommproduct.entity.ProductStatus
import java.time.ZonedDateTime
import java.util.UUID

data class ProductResponseDto(
    val id: UUID,
    val title: String,
    val description: String,
    val price: Double,
    var categoryId: Long?,
    val attributes: Map<String, String> = mapOf(),
    val status: ProductStatus,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime
)
