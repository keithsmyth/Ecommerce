package com.keithsmyth.ecommerce.ui.dashboard

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
fun DashboardRoute(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    val viewState by dashboardViewModel.viewState.collectAsStateWithLifecycle()
    DashboardScreen(
        viewState = viewState,
        onLoginClick = navigateToLoginScreen,
    )
}

@Composable
private fun DashboardScreen(
    viewState: DashboardViewState,
    onLoginClick: () -> Unit,
) {
    when (viewState) {
        is DashboardViewState.Ready -> DashboardReadyScreen(
            readyViewState = viewState,
            onLoginClick = onLoginClick,
        )
        DashboardViewState.Loading -> FullScreenLoading()
    }
}

@Composable
private fun DashboardReadyScreen(
    readyViewState: DashboardViewState.Ready,
    onLoginClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column {
            Text(
                modifier = Modifier.padding(paddingValues),
                text = stringResource(id = R.string.welcome, readyViewState.username)
            )
            if (readyViewState.showLoginButton) {
                Button(onClick = onLoginClick) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }
    }
}
