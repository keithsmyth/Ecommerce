package com.keithsmyth.ecommerce.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.keithsmyth.ecommerce.R
import com.keithsmyth.ecommerce.ui.account.AccountRoute
import com.keithsmyth.ecommerce.ui.dashboard.DashboardRoute
import com.keithsmyth.ecommerce.ui.product.ProductArguments
import com.keithsmyth.ecommerce.ui.product.ProductRoute
import com.keithsmyth.ecommerce.ui.search.SearchRoute
import com.keithsmyth.ecommerce.ui.specials.SpecialsRoute

/**
 * Encapsulate route strings
 * Encapsulate navigation compose classes
 */
@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToLoginScreen: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeNavDestination.dashboard,
    ) {
        composable(route = HomeNavDestination.dashboard) {
            DashboardRoute(navigateToLoginScreen = navigateToLoginScreen)
        }

        navigation(
            route = HomeNavDestination.searchGraph,
            startDestination = HomeNavDestination.searchHome,
        ) {
            composable(route = HomeNavDestination.searchHome) {
                SearchRoute(
                    navigateToProduct = { productId ->
                        navController.navigate("${HomeNavDestination.searchProduct}/$productId")
                    }
                )
            }

            composable(
                route = "${HomeNavDestination.searchProduct}/{${ProductArguments.productId}}",
                arguments = listOf(
                    navArgument(ProductArguments.productId) { type = NavType.IntType },
                ),
            ) {
                ProductRoute(
                    navigateBack = navController::navigateUp,
                )
            }
        }

        composable(route = HomeNavDestination.specials) {
            SpecialsRoute()
        }

        composable(route = HomeNavDestination.account) {
            AccountRoute()
        }
    }
}

private object HomeNavDestination {
    const val dashboard = "dashboard"
    const val searchGraph = "search-graph"
    const val specials = "specials"
    const val account = "account"
    const val searchHome = "search-home"
    const val searchProduct = "search-product"
}

data class HomeNavRootDestination(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val contentDescriptionResId: Int,
) {
    companion object {
        val rootDestinations = listOf(
            HomeNavRootDestination(
                route = HomeNavDestination.dashboard,
                iconResId = R.drawable.ic_dashboard,
                contentDescriptionResId = R.string.dashboard,
            ),
            HomeNavRootDestination(
                route = HomeNavDestination.searchGraph,
                iconResId = R.drawable.ic_search,
                contentDescriptionResId = R.string.search,
            ),
            HomeNavRootDestination(
                route = HomeNavDestination.specials,
                iconResId = R.drawable.ic_specials,
                contentDescriptionResId = R.string.specials,
            ),
            HomeNavRootDestination(
                route = HomeNavDestination.account,
                iconResId = R.drawable.ic_account,
                contentDescriptionResId = R.string.account,
            ),
        )
    }
}
