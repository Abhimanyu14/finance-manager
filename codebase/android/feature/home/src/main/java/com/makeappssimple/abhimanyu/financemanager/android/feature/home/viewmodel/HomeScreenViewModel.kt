package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : ScreenViewModel {
    val homeListItemViewData: Flow<List<TransactionListItemData>>
    val isBackupCardVisible: Flow<Boolean>
    val logger: Logger
    val navigationManager: NavigationManager

    fun backupDataToDocument(
        uri: Uri,
    )
}
