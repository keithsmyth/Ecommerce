package com.keithsmyth.ecommerce.ui.specials

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keithsmyth.ecommerce.ui.common.FullScreenLoading

@Composable
fun SpecialsRoute(specialsViewModel: SpecialsViewModel = hiltViewModel()) {
    val viewState by specialsViewModel.viewState.collectAsStateWithLifecycle()
    SpecialsScreen(viewState)
}

@Composable
private fun SpecialsScreen(viewState: SpecialsViewState) {
    when (viewState) {
        is SpecialsViewState.Ready -> SpecialsReadyScreen()
        SpecialsViewState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun SpecialsReadyScreen() {
    Scaffold { paddingValues ->
        Text(
            modifier = Modifier.padding(paddingValues),
            text = "Specials"
        )
    }
}
