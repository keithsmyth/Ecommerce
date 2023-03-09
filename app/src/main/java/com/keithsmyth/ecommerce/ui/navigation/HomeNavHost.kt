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
import com.keithsmyth.ecommerce.ui.product.ProductRoute
import com.keithsmyth.ecommerce.ui.search.SearchRoute
import com.keithsmyth.ecommerce.ui.specials.SpecialsRoute

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToLoginScreen: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeNavDestination.Dashboard.route,
    ) {
        composable(route = HomeNavDestination.Dashboard.route) {
            DashboardRoute(navigateToLoginScreen = navigateToLoginScreen)
        }

        navigation(
            route = HomeNavDestination.Search.route,
            startDestination = HomeNavDestination.SearchHome.route,
        ) {
            composable(route = HomeNavDestination.SearchHome.route) {
                SearchRoute(
                    navigateToProduct = {}
                )
            }

            composable(
                route = HomeNavDestination.SearchProduct.route,
                arguments = HomeNavDestination.SearchProduct.arguments,
            ) {
                ProductRoute()
            }
        }

        composable(route = HomeNavDestination.Specials.route) {
            SpecialsRoute()
        }

        composable(route = HomeNavDestination.Account.route) {
            AccountRoute()
        }
    }
}

sealed class HomeNavDestination(val route: String) {
    object Dashboard : EcommerceNavDestination("dashboard")

    object Search : EcommerceNavDestination("search")

    object Specials : EcommerceNavDestination("specials")

    object Account : EcommerceNavDestination("account")

    object SearchHome : EcommerceNavDestination("search-home")

    object SearchProduct : EcommerceNavDestination("search-product") {
        val arguments = listOf(
            navArgument("product_id") { type = NavType.IntType },
        )
    }

    companion object {
        val rootDestinations = listOf(
            HomeNavRootDestination(
                route = Dashboard.route,
                iconResId = R.drawable.ic_dashboard,
                contentDescriptionResId = R.string.dashboard,
            ),
            HomeNavRootDestination(
                route = Search.route,
                iconResId = R.drawable.ic_search,
                contentDescriptionResId = R.string.search,
            ),
            HomeNavRootDestination(
                route = Specials.route,
                iconResId = R.drawable.ic_specials,
                contentDescriptionResId = R.string.specials,
            ),
            HomeNavRootDestination(
                route = Account.route,
                iconResId = R.drawable.ic_account,
                contentDescriptionResId = R.string.account,
            ),
        )
    }
}

data class HomeNavRootDestination(
    val route: String,
    @DrawableRes val iconResId: Int,
    @StringRes val contentDescriptionResId: Int,
)
