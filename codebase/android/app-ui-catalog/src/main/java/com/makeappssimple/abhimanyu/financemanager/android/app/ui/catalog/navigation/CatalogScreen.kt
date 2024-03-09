package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation

sealed class CatalogScreen(
    val route: String,
) {
    data object Color : CatalogScreen(
        route = "color",
    )

    data object DefaultTag : CatalogScreen(
        route = "default_tag",
    )

    data object EmojiCircle : CatalogScreen(
        route = "emoji_circle",
    )

    data object Home : CatalogScreen(
        route = "home",
    )

    data object NavigationBackButton : CatalogScreen(
        route = "navigation_back_button",
    )

    data object OutlinedTextField : CatalogScreen(
        route = "outlined_text_field",
    )

    data object OverviewCard : CatalogScreen(
        route = "overview_card",
    )

    data object OverviewTab : CatalogScreen(
        route = "overview_tab",
    )

    data object SaveButton : CatalogScreen(
        route = "save_button",
    )

    data object SearchBar : CatalogScreen(
        route = "search_bar",
    )

    data object SelectionGroup : CatalogScreen(
        route = "selection_group",
    )

    data object Text : CatalogScreen(
        route = "text",
    )

    data object TopAppBar : CatalogScreen(
        route = "top_app_bar",
    )

    data object TotalBalanceCard : CatalogScreen(
        route = "total_balance_card",
    )
}
