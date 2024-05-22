package by.ecomm.ecommproduct.dto

import by.ecomm.ecommproduct.entity.ProductStatus

data class ProductRequestDto(
    val title: String,
    val description: String,
    val price: Double,
    val categoryId: Long,
    val attributes: Map<String, String> = mapOf(),
    val status: ProductStatus,
)
