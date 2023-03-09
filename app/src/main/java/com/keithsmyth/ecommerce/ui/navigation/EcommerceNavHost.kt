package com.keithsmyth.ecommerce.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.keithsmyth.ecommerce.ui.home.HomeRoute
import com.keithsmyth.ecommerce.ui.login.LoginRoute

@Composable
fun EcommerceNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = EcommerceNavDestination.Home.route,
    ) {
        composable(route = EcommerceNavDestination.Home.route) {
            HomeRoute(
                navigateToLoginScreen = navController::navigateToLoginScreen,
            )
        }
        composable(route = EcommerceNavDestination.Login.route) {
            LoginRoute(
                navigateBack = navController::navigateUp,
            )
        }
    }
}

private fun NavHostController.navigateToLoginScreen() {
    navigate(EcommerceNavDestination.Login.route)
}

private fun NavHostController.navigateToHomeScreen() {
    navigate(EcommerceNavDestination.Home.route)
}

private sealed class EcommerceNavDestination(val route: String) {
    object Home : EcommerceNavDestination("home")
    object Login : EcommerceNavDestination("login")
}
