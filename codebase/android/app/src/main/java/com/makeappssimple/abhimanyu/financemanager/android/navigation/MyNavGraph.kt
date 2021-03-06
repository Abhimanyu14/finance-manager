package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.CATEGORY_ID
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.SOURCE_ID
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs.TRANSACTION_ID
import com.makeappssimple.abhimanyu.financemanager.android.ui.activity.MainActivityViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_category.screen.AddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.screen.AddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.screen.AddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.screen.CategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category.screen.EditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.screen.EditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.screen.EditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.screen.HomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.screen.SettingsScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.screen.SourcesScreen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.screen.TransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

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
            Command.NOOP -> {}
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.AddCategory.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.AddCategory.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.AddCategory.route}"
                },
            ),
        ) {
            AddCategoryScreen()
        }

        composable(
            route = Screen.AddSource.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.AddSource.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.AddSource.route}"
                },
            ),
        ) {
            AddSourceScreen()
        }

        composable(
            route = Screen.AddTransaction.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.AddTransaction.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.AddTransaction.route}"
                },
            ),
        ) {
            AddTransactionScreen()
        }

        composable(
            route = Screen.Categories.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Categories.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.Categories.route}"
                },
            ),
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
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Home.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.Home.route}"
                },
            ),
        ) {
            HomeScreen()
        }

        composable(
            route = Screen.Settings.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Settings.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.Settings.route}"
                },
            ),
        ) {
            SettingsScreen()
        }

        composable(
            route = Screen.Sources.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Sources.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.Sources.route}"
                },
            ),
        ) {
            SourcesScreen()
        }

        composable(
            route = Screen.Transactions.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Transactions.route}"
                },
                navDeepLink {
                    uriPattern = "$DEEPLINK_BASE_URL/${Screen.Transactions.route}"
                },
            ),
        ) {
            TransactionsScreen()
        }
    }
}
