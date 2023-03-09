package com.keithsmyth.ecommerce.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keithsmyth.ecommerce.R

object ProductArguments {

    const val productId = "product_id"

    fun retrieveProductId(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Int>(productId)
            ?: throw IllegalStateException("$productId is required")
    }

}

@Composable
fun ProductRoute(
    productViewModel: ProductViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val product by productViewModel.viewState.collectAsStateWithLifecycle()
    ProductScreen(
        product = product,
        onBackClick = navigateBack,
    )
}

@Composable
private fun ProductScreen(
    product: String,
    onBackClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column {
            TopAppBar {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigate_back),
                    )
                }
            }
            Text(
                modifier = Modifier.padding(paddingValues),
                text = "Product\n$product"
            )
        }
    }
}
