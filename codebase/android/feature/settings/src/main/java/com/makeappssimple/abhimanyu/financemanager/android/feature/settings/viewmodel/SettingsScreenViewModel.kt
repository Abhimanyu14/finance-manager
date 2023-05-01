package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel

interface SettingsScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager

    fun backupDataToDocument(
        uri: Uri,
    )

    fun getAppVersionName(): String

    fun navigateToTransactionForValuesScreen()

    fun navigateUp()

    fun restoreDataFromDocument(
        uri: Uri,
    )

    fun recalculateTotal()
}
