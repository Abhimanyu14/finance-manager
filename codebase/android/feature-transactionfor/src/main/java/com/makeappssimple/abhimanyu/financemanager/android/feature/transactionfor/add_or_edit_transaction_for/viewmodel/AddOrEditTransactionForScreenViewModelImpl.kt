package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditTransactionForScreenViewModelImpl @Inject constructor(
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : AddOrEditTransactionForScreenViewModel, ViewModel() {
    private val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesUseCase().defaultListStateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
        )
    private val _title: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val title: StateFlow<String> = _title

    private val _transactionFor: MutableStateFlow<TransactionFor?> = MutableStateFlow(
        value = null,
    )
    override val transactionFor: StateFlow<TransactionFor?> = _transactionFor

    init {
        savedStateHandle.get<Int>(NavArgs.TRANSACTION_FOR_ID)?.let { transactionForId ->
            getTransactionFor(
                id = transactionForId,
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateTransactionFor() {
        val updatedTransactionFor = transactionFor.value?.copy(
            title = title.value,
        ) ?: return
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateTransactionForValuesUseCase(
                updatedTransactionFor,
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun insertTransactionFor() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertTransactionForUseCase(
                TransactionFor(
                    title = title.value,
                )
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun isValidTitle(): Boolean {
        val title = title.value

        // TODO-Abhi: Error message - "Title can not be empty"
        if (title.isBlank()) {
            return false
        }

        // TODO-Abhi: Error message - "Title already exists"
        if (title != transactionFor.value?.title &&
            transactionForValues.value.find {
                it.title.equalsIgnoringCase(
                    other = title,
                )
            }.isNotNull()
        ) {
            return false
        }

        return true
    }

    override fun clearTitle() {
        updateTitle(
            updatedTitle = "",
        )
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        _title.update {
            updatedTitle
        }
    }

    private fun getTransactionFor(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            _transactionFor.update {
                getTransactionForUseCase(
                    id = id,
                )
            }
            updateInitialTransactionForValue()
        }
    }

    private fun updateInitialTransactionForValue() {
        val transactionFor = transactionFor.value ?: return
        updateTitle(
            updatedTitle = transactionFor.title,
        )
    }
}
