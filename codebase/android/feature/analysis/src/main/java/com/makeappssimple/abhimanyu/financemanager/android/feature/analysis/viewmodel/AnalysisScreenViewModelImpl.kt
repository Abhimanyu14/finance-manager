package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataMappedByCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@HiltViewModel
internal class AnalysisScreenViewModelImpl @Inject constructor(
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val getTransactionDataMappedByCategoryUseCase: GetTransactionDataMappedByCategoryUseCase,
) : AnalysisScreenViewModel, ViewModel() {
    private val validTransactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    )
    private val cachedTransactionDataMappedByCategory: MutableMap<TransactionType, List<AnalysisListItemData>> =
        mutableMapOf()

    override val transactionTypesChipItems: List<ChipItem> = validTransactionTypes.map {
        ChipItem(
            text = it.title,
        )
    }

    private val _selectedTransactionTypeIndex: MutableStateFlow<Int?> = MutableStateFlow(
        value = 0,
    )
    override val selectedTransactionTypeIndex: StateFlow<Int?> = _selectedTransactionTypeIndex

    override val transactionDataMappedByCategory: StateFlow<List<AnalysisListItemData>> =
        selectedTransactionTypeIndex.map {
            getTransactionDataMappedByCategoryForSelectedTransactionType(it)
        }.defaultListStateIn(
            scope = viewModelScope,
        )

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        _selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private suspend fun getTransactionDataMappedByCategoryForSelectedTransactionType(
        index: Int?,
    ): List<AnalysisListItemData> {
        val transactionType = index?.run {
            validTransactionTypes.getOrNull(index) ?: TransactionType.EXPENSE
        } ?: TransactionType.EXPENSE

        if (cachedTransactionDataMappedByCategory.containsKey(transactionType)) {
            return cachedTransactionDataMappedByCategory[transactionType] ?: emptyList()
        }

        cachedTransactionDataMappedByCategory[transactionType] =
            getTransactionDataMappedByCategoryUseCase(
                transactionType = transactionType,
            ).map {
                AnalysisListItemData(
                    amountText = Amount(
                        value = it.amountValue,
                    ).toString(),
                    emoji = it.category.emoji,
                    percentage = (it.percentage / 100).toFloat(),
                    percentageText = "%.2f".format(it.percentage).let { percentage ->
                        "$percentage%"
                    },
                    title = it.category.title,
                )
            }
        return cachedTransactionDataMappedByCategory[transactionType] ?: emptyList()
    }
}
