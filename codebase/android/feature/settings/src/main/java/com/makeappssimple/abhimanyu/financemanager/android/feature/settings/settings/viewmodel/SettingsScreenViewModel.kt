package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreenUIEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

public interface SettingsScreenViewModel : ScreenViewModel {
    public val event: SharedFlow<SettingsScreenEvent>
    public val screenUIData: StateFlow<MyResult<SettingsScreenUIData>?>

    public fun backupDataToDocument(
        uri: Uri,
    )

    public fun disableReminder()

    public fun enableReminder()

    public fun handleUIEvents(
        uiEvent: SettingsScreenUIEvent,
    )

    public fun restoreDataFromDocument(
        uri: Uri,
    )

    public fun recalculateTotal()
}
