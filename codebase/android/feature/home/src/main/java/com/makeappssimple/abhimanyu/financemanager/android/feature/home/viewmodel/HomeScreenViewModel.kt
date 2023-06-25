package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<MyResult<HomeScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )
}
