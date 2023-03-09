package com.keithsmyth.ecommerce.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateToRootDestination(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true}
        launchSingleTop = true
        restoreState = true
    }
}
