package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<HomeScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun handleUIEvents(
        uiEvent: HomeScreenUIEvent,
    )
}
