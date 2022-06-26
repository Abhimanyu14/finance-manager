package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel

interface SettingsScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager

    fun backupDataToDocument(
        uri: Uri,
    )

    fun restoreDataFromDocument(
        uri: Uri,
    )

    fun recalculateTotal()
}
