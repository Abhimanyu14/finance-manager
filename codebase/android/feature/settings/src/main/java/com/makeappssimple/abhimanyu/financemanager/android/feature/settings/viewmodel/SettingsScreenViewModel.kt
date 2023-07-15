package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen.SettingsScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface SettingsScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun navigateToCategoriesScreen()

    fun navigateToSourcesScreen()

    fun navigateToTransactionForValuesScreen()

    fun navigateUp()

    fun restoreDataFromDocument(
        uri: Uri,
    )

    fun recalculateTotal()
}
