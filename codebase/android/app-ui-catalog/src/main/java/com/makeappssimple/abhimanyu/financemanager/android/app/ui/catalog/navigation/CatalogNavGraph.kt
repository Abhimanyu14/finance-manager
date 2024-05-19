package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.foundation.color.CatalogColorScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.foundation.text.CatalogTextScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.home.CatalogHomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.default_tag.CatalogDefaultTagScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.emoji_circle.CatalogEmojiCircleScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.navigation_back_button.CatalogNavigationBackButtonScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.outlined_text_field.CatalogOutlinedTextFieldScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.overview_card.CatalogOverviewCardScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.overview_tab.CatalogOverviewTabScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.save_button.CatalogSaveButtonScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.search_bar.CatalogSearchBarScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.selection_group.CatalogSelectionGroupScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.top_app_bar.CatalogTopAppBarScreen
import com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.screen.ui.total_balance_card.CatalogTotalBalanceCardScreen

@Composable
internal fun CatalogNavGraph() {
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
            CatalogColorScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.DefaultTag.route,
        ) {
            CatalogDefaultTagScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.EmojiCircle.route,
        ) {
            CatalogEmojiCircleScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.Home.route,
        ) {
            CatalogHomeScreen(
                navigateTo = navigateTo,
            )
        }
        composable(
            route = CatalogScreen.NavigationBackButton.route,
        ) {
            CatalogNavigationBackButtonScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OutlinedTextField.route,
        ) {
            CatalogOutlinedTextFieldScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OverviewCard.route,
        ) {
            CatalogOverviewCardScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.OverviewTab.route,
        ) {
            CatalogOverviewTabScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SaveButton.route,
        ) {
            CatalogSaveButtonScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SearchBar.route,
        ) {
            CatalogSearchBarScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.SelectionGroup.route,
        ) {
            CatalogSelectionGroupScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.Text.route,
        ) {
            CatalogTextScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.TopAppBar.route,
        ) {
            CatalogTopAppBarScreen(
                navigateUp = navigateUp,
            )
        }
        composable(
            route = CatalogScreen.TotalBalanceCard.route,
        ) {
            CatalogTotalBalanceCardScreen(
                navigateUp = navigateUp,
            )
        }
    }
}
