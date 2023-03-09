package com.keithsmyth.ecommerce.domain

import com.keithsmyth.ecommerce.data.ProductRepository
import com.keithsmyth.ecommerce.model.ProductModel
import com.keithsmyth.ecommerce.model.ProductPreviewModel
import javax.inject.Inject

class ProductController @Inject constructor(
    private val productRepository: ProductRepository,
) {

    suspend fun allPreviews(): List<ProductPreviewModel> = productRepository.fetchPreviews()

    suspend fun product(id: Int): ProductModel = productRepository.fetchProduct(id)

}
