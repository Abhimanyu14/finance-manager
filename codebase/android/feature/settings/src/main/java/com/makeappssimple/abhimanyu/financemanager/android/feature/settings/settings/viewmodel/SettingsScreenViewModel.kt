package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface SettingsScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun disableReminder()

    fun enableReminder()

    fun handleUIEvents(
        uiEvent: SettingsScreenUIEvent,
    )

    fun restoreDataFromDocument(
        uri: Uri,
    )

    fun recalculateTotal()
}
