package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SourcesViewModelImpl @Inject constructor(
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    override val navigationManager: NavigationManager,
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
) : SourcesViewModel, ViewModel() {
    override val sources: Flow<List<Source>> = getSourcesUseCase()
    override val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSource(
        source: Source,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            updateSourcesUseCase(
                source,
            )
        }
    }

    override fun deleteSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            deleteSourceUseCase(
                id = id,
            )
        }
    }

    override fun insertTransaction(
        amountValue: Long,
        sourceTo: Source,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
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
}
