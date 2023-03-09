package com.keithsmyth.ecommerce.ui.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keithsmyth.ecommerce.R
import com.keithsmyth.ecommerce.ui.common.FullScreenLoading

@Composable
fun AccountRoute(accountViewModel: AccountViewModel = hiltViewModel()) {
    val viewState by accountViewModel.viewState.collectAsStateWithLifecycle()
    AccountScreen(
        viewState = viewState,
        onLogoutClick = accountViewModel::logout,
    )
}

@Composable
private fun AccountScreen(
    viewState: AccountViewState,
    onLogoutClick: () -> Unit,
) {
    when (viewState) {
        is AccountViewState.Ready -> AccountReadyScreen(
            readyViewState = viewState,
            onLogoutClick = onLogoutClick,
        )
        AccountViewState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun AccountReadyScreen(
    readyViewState: AccountViewState.Ready,
    onLogoutClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column {
            Text(
                modifier = Modifier.padding(paddingValues),
                text = "Account"
            )
            Text(
                modifier = Modifier.padding(paddingValues),
                text = readyViewState.username
            )
            if (readyViewState.showLogoutButton) {
                Button(onClick = onLogoutClick) {
                    Text(text = stringResource(id = R.string.logout))
                }
            }
        }
    }
}
