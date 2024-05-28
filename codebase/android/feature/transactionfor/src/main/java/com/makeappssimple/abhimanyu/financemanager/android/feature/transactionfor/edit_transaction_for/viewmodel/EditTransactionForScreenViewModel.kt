package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.EditTransactionForScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class EditTransactionForScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
    private val navigator: Navigator,
    private val updateTransactionForValuesUseCase: UpdateTransactionForValuesUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = EditTransactionForScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // region transactionForValues
    private val _transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    public val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        _transactionForValues
    // endregion

    // region transactionFor
    private val _transactionFor: MutableStateFlow<TransactionFor?> = MutableStateFlow(
        value = null,
    )
    public val transactionFor: StateFlow<TransactionFor?> = _transactionFor
    // endregion

    public fun initViewModel() {
        fetchData()
    }

    public fun updateTransactionFor(
        title: String,
    ) {
        val updatedTransactionFor = transactionFor.value?.copy(
            title = title,
        ) ?: return
        viewModelScope.launch {
            updateTransactionForValuesUseCase(
                updatedTransactionFor,
            )
            navigator.navigateUp()
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getAllTransactionForValues()
        getOriginalTransactionFor()
    }

    private fun getAllTransactionForValues() {
        viewModelScope.launch {
            _transactionForValues.update {
                getAllTransactionForValuesUseCase()
            }
        }
    }

    private fun getOriginalTransactionFor() {
        screenArgs.originalTransactionForId?.let { id ->
            viewModelScope.launch {
                _transactionFor.update {
                    getTransactionForUseCase(
                        id = id,
                    )
                }
            }
        }
    }
}
