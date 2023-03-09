package com.keithsmyth.ecommerce.model

data class ProductPreviewModel(
    val id: Int,
    val name: String,
    val thumbnailUrl: String,
)

data class ProductModel(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
)
