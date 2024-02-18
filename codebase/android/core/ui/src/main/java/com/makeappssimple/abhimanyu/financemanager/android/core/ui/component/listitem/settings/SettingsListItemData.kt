package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

sealed interface SettingsListItemData {
    val contentType: SettingsListItemContentType
}

enum class SettingsListItemContentType(
    val title: String,
) {
    APP_VERSION(
        title = "App Version"
    ),
    DIVIDER(
        title = "Divider"
    ),
    MENU(
        title = "Menu"
    ),
    MENU_SECTION_TITLE(
        title = "Menu Section Title"
    ),
}
