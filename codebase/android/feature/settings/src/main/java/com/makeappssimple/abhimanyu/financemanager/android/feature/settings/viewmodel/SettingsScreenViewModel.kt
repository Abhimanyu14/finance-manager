package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen.SettingsScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface SettingsScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun navigateToTransactionForValuesScreen()

    fun navigateUp()

    fun restoreDataFromDocument(
        uri: Uri,
    )

    fun recalculateTotal()
}
