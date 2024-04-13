package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface HomeScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<HomeScreenUIData>?>

    public fun backupDataToDocument(
        uri: Uri,
    )

    public fun handleUIEvents(
        uiEvent: HomeScreenUIEvent,
    )
}
