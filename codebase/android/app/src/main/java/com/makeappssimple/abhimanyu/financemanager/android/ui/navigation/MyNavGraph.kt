package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.financemanager.android.ui.activity.MainActivityViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.AddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.HomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterial3Api
@Composable
fun MyNavGraph(
    activityViewModel: MainActivityViewModel,
) {
    logError("Inside MyNavGraph")
    val navHostController = rememberNavController()

    activityViewModel.navigationManager.command.collectAsState().value.also { command ->
        when (command.command) {
            Command.NAVIGATE -> {
                navHostController.navigate(
                    route = command.destination,
                )

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    direction = MyNavigationDirections.default()
                )
            }
            Command.NAVIGATE_UP -> {
                navHostController.navigateUp()

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    direction = MyNavigationDirections.default()
                )
            }
            Command.CLEAR_BACKSTACK_AND_NAVIGATE -> {
                navHostController.navigate(
                    route = command.destination,
                ) {
                    popUpTo(
                        id = navHostController.graph.findStartDestination().id,
                    ) {
                        inclusive = true
                    }
                }

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    direction = MyNavigationDirections.default()
                )
            }
            Command.CLEAR_TILL_ROOT -> {
                navHostController.popBackStack(
                    destinationId = navHostController.graph.findStartDestination().id,
                    inclusive = false,
                )

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    direction = MyNavigationDirections.default()
                )
            }
            Command.NOOP -> {
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.AddTransaction.route,
        ) {
            AddTransactionScreen()
        }
    }
}
