package com.keithsmyth.ecommerce.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
private fun HomeBottomBar(
    currentDestination: NavDestination?,
    onNavigationItemClick: (String) -> Unit,
) {
    BottomNavigation {
        HomeNavRootDestination.rootDestinations.forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination
                    ?.hierarchy
                    ?.any { it.route == destination.route } == true,
                onClick = { onNavigationItemClick(destination.route) },
                icon = {
                    Icon(
                        painter = painterResource(destination.iconResId),
                        contentDescription = stringResource(destination.contentDescriptionResId),
                    )
                },
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreen(navigateToLoginScreen = {})
}
