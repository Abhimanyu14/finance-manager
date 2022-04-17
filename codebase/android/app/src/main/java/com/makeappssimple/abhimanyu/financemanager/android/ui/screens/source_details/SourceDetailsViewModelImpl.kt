package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.source_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SourceDetailsViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : SourceDetailsViewModel, ViewModel() {
    private val _source: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    override val source: StateFlow<Source?> = _source

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun deleteSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteSourceUseCase(
                id = id,
            )
        }
    }

    override fun getSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            _source.value = getSourceUseCase(
                id = id,
            )
        }
    }

    override fun insertTransaction(
        amountValue: Long,
        sourceTo: Source,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertTransactionUseCase(
                transaction = Transaction(
                    amount = Amount(
                        value = amountValue,
                    ),
                    categoryId = 0,
                    sourceFromId = 0,
                    sourceToId = sourceTo.id,
                    description = "",
                    title = TransactionType.ADJUSTMENT.title,
                    creationTimestamp = Calendar.getInstance().timeInMillis,
                    transactionTimestamp = Calendar.getInstance().timeInMillis,
                    transactionFor = TransactionFor.SELF,
                    transactionType = TransactionType.ADJUSTMENT,
                ),
            )
            updateSource(
                source = sourceTo,
            )
        }
    }

    private fun updateSource(
        source: Source,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateSourcesUseCase(
                source,
            )
        }
    }
}
