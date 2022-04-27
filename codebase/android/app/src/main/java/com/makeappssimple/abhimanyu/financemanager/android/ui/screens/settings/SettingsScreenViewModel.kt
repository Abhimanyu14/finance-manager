package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel

interface SettingsScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager

    fun backupDataToDocument(
        uri: Uri,
    )

    fun restoreDataFromDocument(
        uri: Uri,
    )
}