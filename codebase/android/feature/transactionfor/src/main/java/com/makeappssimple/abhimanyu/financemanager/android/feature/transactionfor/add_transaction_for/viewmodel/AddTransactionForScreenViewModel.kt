package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionForScreenViewModel @Inject constructor(
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val insertTransactionForUseCase: InsertTransactionForValuesUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val _transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>> =
        MutableStateFlow(
            persistentListOf()
        )
    public val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        _transactionForValues

    public fun initViewModel() {
        fetchData()
    }

    public fun insertTransactionFor(
        transactionFor: TransactionFor,
    ) {
        viewModelScope.launch {
            insertTransactionForUseCase(transactionFor)
            navigator.navigateUp()
        }
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    private fun fetchData() {
        getAllTransactionForValues()
    }

    private fun getAllTransactionForValues() {
        viewModelScope.launch {
            _transactionForValues.update {
                getAllTransactionForValuesUseCase()
            }
        }
    }
}
