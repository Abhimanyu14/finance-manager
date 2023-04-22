package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val homeListItemViewData: Flow<List<TransactionData>>
    val showBackupCard: Flow<Boolean>

    fun backupDataToDocument(
        uri: Uri,
    )
}
