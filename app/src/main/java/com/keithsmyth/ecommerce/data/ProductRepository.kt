package com.keithsmyth.ecommerce.data

import com.keithsmyth.ecommerce.model.ProductModel
import com.keithsmyth.ecommerce.model.ProductPreviewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor() {

    private val previews: List<ProductPreviewModel> by lazy {
        mutableListOf<ProductPreviewModel>().apply {
            for (i in 0..99) {
                add(
                    ProductPreviewModel(
                        id = i,
                        name = "Product $i",
                        thumbnailUrl = "https://placekitten.com/50/50",
                    )
                )
            }
        }
    }

    suspend fun fetchPreviews(): List<ProductPreviewModel> {
        return withContext(Dispatchers.IO) {
            simulateDelay()
            previews
        }
    }

    suspend fun fetchProduct(id: Int): ProductModel {
        return withContext(Dispatchers.IO) {
            simulateDelay()
            ProductModel(
                id = id,
                name = "Product $id",
                imageUrl = "https://placekitten.com/300/200",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            )
        }
    }

    private suspend fun simulateDelay() {
        delay(1000L)
    }
}
