package com.keithsmyth.ecommerce.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.keithsmyth.ecommerce.model.ProductPreviewModel
import com.keithsmyth.ecommerce.ui.common.FullScreenLoading

@Composable
fun SearchRoute(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navigateToProduct: (Int) -> Unit,
) {
    val viewState by searchViewModel.viewState.collectAsStateWithLifecycle()
    SearchScreen(
        viewState = viewState,
        onPreviewClicked = navigateToProduct,
    )
}

@Composable
private fun SearchScreen(
    viewState: SearchViewState,
    onPreviewClicked: (Int) -> Unit,
) {
    when (viewState) {
        is SearchViewState.Ready -> SearchReadyScreen(
            readyViewState = viewState,
            onPreviewClicked = onPreviewClicked,
        )
        SearchViewState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun SearchReadyScreen(
    readyViewState: SearchViewState.Ready,
    onPreviewClicked: (Int) -> Unit,
) {
    Scaffold { paddingValues ->
        Column {
            TopAppBar {

            }
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(readyViewState.previews, key = { it.id }) { previewModel ->
                    Row(
                        modifier = Modifier
                            .clickable { onPreviewClicked(previewModel.id) }
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = previewModel.name,
                            style = MaterialTheme.typography.body1,
                        )
                        AsyncImage(
                            model = previewModel.thumbnailUrl,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    SearchReadyScreen(
        readyViewState = SearchViewState.Ready(
            listOf(
                ProductPreviewModel(
                    id = 1,
                    name = "Name",
                    thumbnailUrl = "https://placekitten.com/50/50"
                ),
            )
        ),
        onPreviewClicked = {},
    )
}
