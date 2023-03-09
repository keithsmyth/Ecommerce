package com.keithsmyth.ecommerce.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keithsmyth.ecommerce.domain.ProductController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val productController: ProductController,
) : ViewModel() {

    private val mutableViewState = MutableStateFlow("")

    val viewState: StateFlow<String> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            val productId = ProductArguments.retrieveProductId(state)
            val product = productController.product(productId)
            mutableViewState.emit(product.toString())
        }
    }

}
