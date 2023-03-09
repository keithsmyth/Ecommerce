package com.keithsmyth.ecommerce.ui.product

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductRoute(productViewModel: ProductViewModel = hiltViewModel()) {
    ProductScreen()
}

@Composable
private fun ProductScreen() {
    Scaffold { paddingValues ->
        Text(
            modifier = Modifier.padding(paddingValues),
            text = "Product"
        )
    }
}
