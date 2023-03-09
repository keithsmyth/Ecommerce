package com.keithsmyth.ecommerce.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keithsmyth.ecommerce.domain.ProductController
import com.keithsmyth.ecommerce.model.ProductPreviewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    productController: ProductController,
) : ViewModel() {

    private val mutableViewState = MutableStateFlow<SearchViewState>(SearchViewState.Loading)

    val viewState: StateFlow<SearchViewState> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            val previews = productController.allPreviews()
            mutableViewState.emit(SearchViewState.Ready(previews))
        }
    }
}

sealed class SearchViewState {
    object Loading : SearchViewState()
    data class Ready(val previews: List<ProductPreviewModel>) : SearchViewState()
}
