package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

sealed interface SettingsListItemData {
    val type: SettingsListItemType
}

enum class SettingsListItemType(
    val title: String,
) {
    APP_VERSION(
        title = "App Version"
    ),
    CONTENT(
        title = "Content"
    ),
    DIVIDER(
        title = "Divider"
    ),
    HEADER(
        title = "Header"
    ),
}
