package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Command
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.accountsNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.navigation.analysisNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.categoriesNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.navigation.homeNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.navigation.settingsNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.transactionForNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.transactionsNavGraph

@Composable
internal fun MyNavGraph(
    activityViewModel: MainActivityViewModel,
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside MyNavGraph",
    )
    val navHostController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(
        key1 = Unit,
    ) {
        activityViewModel.navigationManager.command.collect { command ->
            keyboardController?.hide()
            when (command.command) {
                Command.NAVIGATE -> {
                    navHostController.navigate(
                        route = command.destination,
                    )
                }

                Command.NAVIGATE_UP -> {
                    navHostController.navigateUp()
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
                }

                Command.CLEAR_TILL_ROOT -> {
                    navHostController.popBackStack(
                        destinationId = navHostController.graph.findStartDestination().id,
                        inclusive = false,
                    )
                }

                Command.NOOP -> {}
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        analysisNavGraph()
        categoriesNavGraph()
        homeNavGraph()
        settingsNavGraph()
        accountsNavGraph()
        transactionForNavGraph()
        transactionsNavGraph()
    }
}
