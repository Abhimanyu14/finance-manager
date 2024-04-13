package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

public sealed interface SettingsListItemData {
    public val type: SettingsListItemType
}

public enum class SettingsListItemType(
    public val title: String,
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
