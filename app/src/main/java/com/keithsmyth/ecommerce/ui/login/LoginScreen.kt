package com.keithsmyth.ecommerce.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.keithsmyth.ecommerce.R
import com.keithsmyth.ecommerce.ui.common.FullScreenLoading

@Composable
fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    /**
    // If application does not have Guest access, we can override user back button behaviour
    // to close the app or return to a safe screen.
    BackHandler { navigateToExitApplication() }
     **/

    val viewState by loginViewModel.viewState.collectAsStateWithLifecycle()
    LoginScreen(
        viewState = viewState,
        onLoginClick = loginViewModel::login,
        navigateBack = navigateBack,
    )
}

@Composable
private fun LoginScreen(
    viewState: LoginViewState,
    onLoginClick: () -> Unit,
    navigateBack: () -> Unit
) {
    when (viewState) {
        LoginViewState.Ready -> LoginReadyState(onLoginClick = onLoginClick)
        LoginViewState.LoggingIn -> FullScreenLoading()
        LoginViewState.LoggedIn -> navigateBack()
    }
}

@Composable
private fun LoginReadyState(onLoginClick: () -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Button(onClick = onLoginClick) {
                Text(text = stringResource(R.string.perform_login))
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    LoginScreen(
        viewState = LoginViewState.Ready,
        onLoginClick = {},
        navigateBack = {},
    )
}
