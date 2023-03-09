package com.keithsmyth.ecommerce.ui.home

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.keithsmyth.ecommerce.ui.navigation.HomeNavDestination
import com.keithsmyth.ecommerce.ui.navigation.HomeNavHost
import com.keithsmyth.ecommerce.ui.navigation.HomeNavRootDestination
import com.keithsmyth.ecommerce.ui.navigation.navigateToRootDestination

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit,
) {
    HomeScreen(
        navigateToLoginScreen = navigateToLoginScreen,
    )
}

@Composable
private fun HomeScreen(
    navigateToLoginScreen: () -> Unit,
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = {
            HomeBottomBar(
                currentDestination = navBackStackEntry?.destination,
                onNavigationItemClick = homeNavController::navigateToRootDestination
            )
        }
    ) { paddingValues ->
        HomeNavHost(
            modifier = Modifier.padding(paddingValues),
            navController = homeNavController,
            navigateToLoginScreen = navigateToLoginScreen,
        )
    }
}

@Composable
private fun HomeTopBar() {
    TopAppBar {

    }
}

@Composable
private fun HomeBottomBar(
    currentDestination: NavDestination?,
    onNavigationItemClick: (String) -> Unit,
) {
    BottomNavigation {
        HomeNavDestination.rootDestinations.forEach { rootDestination ->
            HomeBottomNavigationItem(
                rootDestination = rootDestination,
                currentDestination = currentDestination,
                onNavigationItemClick = onNavigationItemClick,
            )
        }
    }
}

@Composable
private fun RowScope.HomeBottomNavigationItem(
    rootDestination: HomeNavRootDestination,
    currentDestination: NavDestination?,
    onNavigationItemClick: (String) -> Unit,
) {
    BottomNavigationItem(
        selected = currentDestination
            ?.hierarchy
            ?.any { it.route == rootDestination.route } == true,
        onClick = { onNavigationItemClick(rootDestination.route) },
        icon = {
            Icon(
                painter = painterResource(rootDestination.iconResId),
                contentDescription = stringResource(rootDestination.contentDescriptionResId),
            )
        },
    )
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreen(navigateToLoginScreen = {})
}
