package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen.AddTransactionForScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen.AddTransactionForScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.AddTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionForScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val navigator: Navigator,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    private val transactionForValues: MutableList<TransactionFor> = mutableListOf()
    private val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val transactionFor: MutableStateFlow<TransactionFor?> = MutableStateFlow(
        value = null,
    )
    private val isValidTransactionForData = combine(
        title,
        transactionFor,
    ) {
            title,
            transactionFor,
        ->
        checkIfTransactionForDataIsValid(
            title = title.text,
            transactionFor = transactionFor,
        )
    }
    private val titleTextFieldError: MutableStateFlow<AddTransactionForScreenUIError?> =
        MutableStateFlow(
            value = null,
        )

    public val screenUIData: StateFlow<MyResult<AddTransactionForScreenUIData>?> = combine(
        title,
        isValidTransactionForData,
        titleTextFieldError,
    ) {
            title,
            isValidTransactionForData,
            titleTextFieldError,
        ->
        if (isValidTransactionForData.isNull()) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddTransactionForScreenUIData(
                    isValidTransactionForData = isValidTransactionForData,
                    title = title,
                    titleTextFieldError = titleTextFieldError,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    public fun initViewModel() {
        getAllTransactionForValues()
        getOriginalTransactionFor()
    }

    public fun insertTransactionFor() {
        viewModelScope.launch {
            insertTransactionForUseCase(
                TransactionFor(
                    title = title.value.text,
                )
            )
            navigator.navigateUp()
        }
    }

    public fun updateTransactionFor() {
        val updatedTransactionFor = transactionFor.value?.copy(
            title = title.value.text,
        ) ?: return
        viewModelScope.launch {
            updateTransactionForValuesUseCase(
                updatedTransactionFor,
            )
            navigator.navigateUp()
        }
    }

    public fun clearTitle() {
        updateTitle(
            updatedTitle = title.value.copy(
                text = "",
            ),
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun getAllTransactionForValues() {
        viewModelScope.launch {
            transactionForValues.clear()
            transactionForValues.addAll(getAllTransactionForValuesUseCase())
        }
    }

    private fun getOriginalTransactionFor() {
        screenArgs.originalTransactionForId?.let { id ->
            viewModelScope.launch {
                transactionFor.update {
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
        title.update {
            it.copy(
                text = transactionFor.title,
                selection = TextRange(transactionFor.title.length),
            )
        }
    }

    private fun checkIfTransactionForDataIsValid(
        title: String,
        transactionFor: TransactionFor?,
    ): Boolean {
        titleTextFieldError.update {
            null
        }
        if (title.isBlank()) {
            return false
        }
        return if (title != transactionFor?.title && transactionForValues.find {
                it.title.equalsIgnoringCase(
                    other = title,
                )
            }.isNotNull()
        ) {
            titleTextFieldError.update {
                AddTransactionForScreenUIError.EXISTS
            }
            false
        } else {
            true
        }
    }
}
