package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditTransactionForScreenViewModelImpl @Inject constructor(
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : AddOrEditTransactionForScreenViewModel, ViewModel() {
    // Navigation parameters
    private var originalTransactionForId: Int? =
        savedStateHandle.get<Int>(NavArgs.TRANSACTION_FOR_ID)

    private val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
        )
    private val _title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(
            text = "",
        ),
    )
    override val title: StateFlow<TextFieldValue> = _title

    private val _transactionFor: MutableStateFlow<TransactionFor?> = MutableStateFlow(
        value = null,
    )
    override val transactionFor: StateFlow<TransactionFor?> = _transactionFor

    init {
        originalTransactionForId?.let {
            getTransactionFor(
                id = it,
            )
        }
    }

    override fun updateTransactionFor() {
        val updatedTransactionFor = transactionFor.value?.copy(
            title = title.value.text,
        ) ?: return
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateTransactionForValuesUseCase(
                updatedTransactionFor,
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun insertTransactionFor() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertTransactionForUseCase(
                TransactionFor(
                    title = title.value.text,
                )
            )
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun isValidTitle(): Boolean {
        val title = title.value.text

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
            updatedTitle = title.value.copy(
                text = "",
            ),
        )
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
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
        _title.update {
            it.copy(
                text = transactionFor.title,
                selection = TextRange(transactionFor.title.length),
            )
        }
    }
}
