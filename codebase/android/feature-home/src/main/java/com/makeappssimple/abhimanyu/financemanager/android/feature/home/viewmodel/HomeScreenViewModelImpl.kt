package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetRecentTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    dataStore: MyDataStore,
    getRecentTransactionDataUseCase: GetRecentTransactionDataUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val backupDataUseCase: BackupDataUseCase,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
) : HomeScreenViewModel, ViewModel() {
    private val lastDataChangeTimestamp: Flow<Long?> = dataStore.getLastDataChangeTimestamp()
    private val lastDataBackupTimestamp: Flow<Long?> = dataStore.getLastDataBackupTimestamp()

    override val homeListItemViewData: Flow<List<TransactionData>> =
        getRecentTransactionDataUseCase()
    override val showBackupCard: Flow<Boolean> = combine(
        lastDataBackupTimestamp, lastDataChangeTimestamp
    ) { lastDataBackupTimestamp, lastDataChangeTimestamp ->
        lastDataBackupTimestamp != null &&
                lastDataChangeTimestamp != null &&
                lastDataBackupTimestamp < lastDataChangeTimestamp
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
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
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    // TODO-Abhi: Clean up unused code
    override fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionAndRevertOtherDataUseCase(
                id = id,
            )
        }
    }
}
