package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.SOURCE_ID
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.TRANSACTION_ID
import com.makeappssimple.abhimanyu.financemanager.android.ui.activity.MainActivityViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.AddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.AddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.AddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.CategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.EditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.EditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.EditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.screen.HomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.SettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.SourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.TransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalComposeUiApi::class,
)
@Composable
fun MyNavGraph(
    activityViewModel: MainActivityViewModel,
) {
    logError(
        message = "Inside MyNavGraph",
    )
    val navHostController = rememberNavController()
    val keyboardController = LocalSoftwareKeyboardController.current

    activityViewModel.navigationManager.command.collectAsState().value.also { command ->
        keyboardController?.hide()
        when (command.command) {
            Command.NAVIGATE -> {
                navHostController.navigate(
                    route = command.destination,
                )

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.default()
                )
            }
            Command.NAVIGATE_UP -> {
                navHostController.navigateUp()

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.default()
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
                    navigationCommand = MyNavigationDirections.default()
                )
            }
            Command.CLEAR_TILL_ROOT -> {
                navHostController.popBackStack(
                    destinationId = navHostController.graph.findStartDestination().id,
                    inclusive = false,
                )

                // TODO-Abhi: Fix this hack to clear previous navigation command
                activityViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.default()
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
            route = Screen.AddCategory.route,
        ) {
            AddCategoryScreen()
        }

        composable(
            route = Screen.AddSource.route,
        ) {
            AddSourceScreen()
        }

        composable(
            route = Screen.AddTransaction.route,
        ) {
            AddTransactionScreen()
        }

        composable(
            route = Screen.Categories.route,
        ) {
            CategoriesScreen()
        }

        composable(
            route = "${Screen.EditCategory.route}/{${CATEGORY_ID}}",
            arguments = listOf(
                navArgument(CATEGORY_ID) {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            EditCategoryScreen(
                categoryId = navBackStackEntry.arguments?.getInt(CATEGORY_ID),
            )
        }

        composable(
            route = "${Screen.EditSource.route}/{${SOURCE_ID}}",
            arguments = listOf(
                navArgument(SOURCE_ID) {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            EditSourceScreen(
                sourceId = navBackStackEntry.arguments?.getInt(SOURCE_ID),
            )
        }

        composable(
            route = "${Screen.EditTransaction.route}/{${TRANSACTION_ID}}",
            arguments = listOf(
                navArgument(TRANSACTION_ID) {
                    type = NavType.IntType
                },
            ),
        ) { navBackStackEntry ->
            EditTransactionScreen(
                transactionId = navBackStackEntry.arguments?.getInt(TRANSACTION_ID),
            )
        }

        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Settings.route,
        ) {
            SettingsScreen()
        }

        composable(
            route = Screen.Sources.route,
        ) {
            SourcesScreen()
        }

        composable(
            route = Screen.Transactions.route,
        ) {
            TransactionsScreen()
        }
    }
}
