package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationArguments
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen.AddCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen.EditCategoryScreen

public fun NavGraphBuilder.categoriesNavGraph() {
    composable(
        route = "${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}",
        arguments = listOf(
            navArgument(NavigationArguments.TRANSACTION_TYPE) {
                type = NavType.StringType
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.AddCategory.route}/{${NavigationArguments.TRANSACTION_TYPE}}"
            },
        ),
    ) {
        AddCategoryScreen()
    }

    composable(
        route = Screen.Categories.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Categories.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Categories.route}"
            },
        ),
    ) {
        CategoriesScreen()
    }

    composable(
        route = "${Screen.EditCategory.route}/{${NavigationArguments.CATEGORY_ID}}",
        arguments = listOf(
            navArgument(NavigationArguments.CATEGORY_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditCategoryScreen()
    }
}
