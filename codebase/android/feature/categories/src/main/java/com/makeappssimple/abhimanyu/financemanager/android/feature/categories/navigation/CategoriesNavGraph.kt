package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen.AddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen.EditCategoryScreen

fun NavGraphBuilder.categoriesNavGraph() {
    composable(
        route = "${Screen.AddCategory.route}/{${NavArgs.TRANSACTION_TYPE}}",
        arguments = listOf(
            navArgument(NavArgs.TRANSACTION_TYPE) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "$DEEPLINK_BROWSER_BASE_URL/${Screen.AddCategory.route}/{${NavArgs.TRANSACTION_TYPE}}"
            },
            navDeepLink {
                uriPattern =
                    "$DEEPLINK_BASE_URL/${Screen.AddCategory.route}/{${NavArgs.TRANSACTION_TYPE}}"
            },
        ),
    ) {
        AddCategoryScreen()
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
        route = "${Screen.EditCategory.route}/{${NavArgs.CATEGORY_ID}}",
        arguments = listOf(
            navArgument(NavArgs.CATEGORY_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditCategoryScreen()
    }
}
