package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
internal class ViewTransactionForScreenViewModelImpl @Inject constructor(
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
) : ViewTransactionForScreenViewModel, ViewModel() {
    override val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesUseCase().defaultListStateIn(
            scope = viewModelScope,
        )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
