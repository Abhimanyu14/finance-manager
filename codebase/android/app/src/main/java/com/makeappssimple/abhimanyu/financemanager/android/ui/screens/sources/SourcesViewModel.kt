package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.models.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val transactionRepository: TransactionRepository,
) : BaseViewModel() {
    val sources: Flow<List<Source>> = sourceRepository.sources
    val sourcesTotalBalanceAmountValue = flow {
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

    fun updateSource(
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

    fun deleteSource(
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

    fun insertTransaction(
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
