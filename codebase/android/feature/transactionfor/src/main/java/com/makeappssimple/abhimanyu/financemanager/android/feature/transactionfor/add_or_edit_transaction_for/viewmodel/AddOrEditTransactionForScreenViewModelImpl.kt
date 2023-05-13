package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.AddOrEditTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AddOrEditTransactionForScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : AddOrEditTransactionForScreenViewModel, ViewModel() {
    private val addOrEditTransactionForScreenArgs: AddOrEditTransactionForScreenArgs =
        AddOrEditTransactionForScreenArgs(
            savedStateHandle = savedStateHandle,
            stringDecoder = stringDecoder,
        )

    private lateinit var transactionForValues: List<TransactionFor>
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
        getAllTransactionForValues()
        getOriginalTransactionFor()
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
        return !(title != transactionFor.value?.title && transactionForValues.find {
            it.title.equalsIgnoringCase(
                other = title,
            )
        }.isNotNull())
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

    private fun getAllTransactionForValues() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            transactionForValues = getAllTransactionForValuesUseCase()
        }
    }

    private fun getOriginalTransactionFor() {
        addOrEditTransactionForScreenArgs.originalTransactionForId?.let { id ->
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
