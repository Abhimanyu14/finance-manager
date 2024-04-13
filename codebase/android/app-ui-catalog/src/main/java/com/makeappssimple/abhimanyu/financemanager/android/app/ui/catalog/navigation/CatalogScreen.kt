package com.makeappssimple.abhimanyu.financemanager.android.app.ui.catalog.navigation

public sealed class CatalogScreen(
    public val route: String,
) {
    public data object Color : CatalogScreen(
        route = "color",
    )

    public data object DefaultTag : CatalogScreen(
        route = "default_tag",
    )

    public data object EmojiCircle : CatalogScreen(
        route = "emoji_circle",
    )

    public data object Home : CatalogScreen(
        route = "home",
    )

    public data object NavigationBackButton : CatalogScreen(
        route = "navigation_back_button",
    )

    public data object OutlinedTextField : CatalogScreen(
        route = "outlined_text_field",
    )

    public data object OverviewCard : CatalogScreen(
        route = "overview_card",
    )

    public data object OverviewTab : CatalogScreen(
        route = "overview_tab",
    )

    public data object SaveButton : CatalogScreen(
        route = "save_button",
    )

    public data object SearchBar : CatalogScreen(
        route = "search_bar",
    )

    public data object SelectionGroup : CatalogScreen(
        route = "selection_group",
    )

    public data object Text : CatalogScreen(
        route = "text",
    )

    public data object TopAppBar : CatalogScreen(
        route = "top_app_bar",
    )

    public data object TotalBalanceCard : CatalogScreen(
        route = "total_balance_card",
    )
}
