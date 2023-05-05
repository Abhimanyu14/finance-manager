package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    dataStore: MyDataStore,
    getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    override val dateTimeUtil: DateTimeUtil, // TODO(Abhi): Change this to private
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val backupDataUseCase: BackupDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : HomeScreenViewModel, ViewModel() {
    override val homeListItemViewData: Flow<List<TransactionData>> =
        getRecentTransactionDataFlowUseCase()
    override val showBackupCard: Flow<Boolean> = combine(
        flow = dataStore.getLastDataBackupTimestamp(),
        flow2 = dataStore.getLastDataChangeTimestamp(),
    ) { lastDataBackupTimestamp, lastDataChangeTimestamp ->
        lastDataBackupTimestamp.isNotNull() &&
                lastDataChangeTimestamp.isNotNull() &&
                lastDataBackupTimestamp < lastDataChangeTimestamp
    }

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }
}
