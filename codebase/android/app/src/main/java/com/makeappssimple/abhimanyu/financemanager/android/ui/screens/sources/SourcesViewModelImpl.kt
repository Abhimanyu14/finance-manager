package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SourcesViewModelImpl @Inject constructor(
    override val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val transactionRepository: TransactionRepository,
) : SourcesViewModel, ViewModel() {
    override val sources: Flow<List<Source>> = sourceRepository.sources
    override val sourcesTotalBalanceAmountValue: Flow<Long> = flow {
        sourceRepository.sources.collectIndexed { _, sources ->
            emit(
                value = sources.sumOf { source ->
                    source.balanceAmount.value
                },
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSource(
        source: Source,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            sourceRepository.updateSources(
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
            sourceRepository.deleteSource(
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
            transactionRepository.insertTransaction(
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
