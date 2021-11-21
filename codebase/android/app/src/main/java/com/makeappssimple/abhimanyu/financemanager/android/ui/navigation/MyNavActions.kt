package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class MyNavActions(
    navHostController: NavHostController,
) {
    val navigateTo = { navBackStackEntry: NavBackStackEntry, route: String ->
        if (navBackStackEntry.lifecycleIsResumed()) {
            navHostController.navigate(route)
        }
    }

    val navigateUp = { navBackStackEntry: NavBackStackEntry ->
        if (navBackStackEntry.lifecycleIsResumed()) {
            navHostController.navigateUp()
        }
    }

    val popBackStackAndNavigate =
        { navBackStackEntry: NavBackStackEntry, route: String?, popUpTo: String, inclusive: Boolean ->
            if (navBackStackEntry.lifecycleIsResumed()) {
                navHostController.popBackStack(popUpTo, inclusive)
                route?.let {
                    navHostController.navigate(route)
                }
            }
        }

    val navigateAfterPopping = { navBackStackEntry: NavBackStackEntry, route: String ->
        if (navBackStackEntry.lifecycleIsResumed()) {
            navHostController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed(): Boolean {
    return this.lifecycle.currentState == Lifecycle.State.RESUMED
}
