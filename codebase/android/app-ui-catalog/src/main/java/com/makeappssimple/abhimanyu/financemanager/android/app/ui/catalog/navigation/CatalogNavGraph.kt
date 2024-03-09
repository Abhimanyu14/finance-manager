package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.foundation.color.ColorScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.foundation.text.TextScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.home.HomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.default_tag.DefaultTagScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.emoji_circle.EmojiCircleScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.navigation_back_button.NavigationBackButtonScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.outlined_text_field.OutlinedTextFieldScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.overview_card.OverviewCardScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.overview_tab.OverviewTabScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.save_button.SaveButtonScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.search_bar.SearchBarScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.selection_group.SelectionGroupScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.top_app_bar.TopAppBarScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screens.ui.total_balance_card.TotalBalanceCardScreen

@Composable
internal fun MyNavGraph() {
    val navHostController = rememberNavController()
    val navigateTo = { route: String ->
        navHostController.navigate(route)
    }
    val navigateUp: () -> Unit = {
        navHostController.navigateUp()
    }

    NavHost(
        navController = navHostController,
        startDestination = CatalogScreen.Home.route,
    ) {
        composable(
            route = CatalogScreen.Color.route,
        ) {
            ColorScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.DefaultTag.route,
        ) {
            DefaultTagScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.EmojiCircle.route,
        ) {
            EmojiCircleScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.Home.route,
        ) {
            HomeScreen(
                navigateTo = navigateTo,
            )
        }
        composable(
            route = CatalogScreen.NavigationBackButton.route,
        ) {
            NavigationBackButtonScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OutlinedTextField.route,
        ) {
            OutlinedTextFieldScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OverviewCard.route,
        ) {
            OverviewCardScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OverviewTab.route,
        ) {
            OverviewTabScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SaveButton.route,
        ) {
            SaveButtonScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SearchBar.route,
        ) {
            SearchBarScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SelectionGroup.route,
        ) {
            SelectionGroupScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.Text.route,
        ) {
            TextScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.TopAppBar.route,
        ) {
            TopAppBarScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.TotalBalanceCard.route,
        ) {
            TotalBalanceCardScreen(
                navigateUp = navigateUp,
            )
        }
    }
}
