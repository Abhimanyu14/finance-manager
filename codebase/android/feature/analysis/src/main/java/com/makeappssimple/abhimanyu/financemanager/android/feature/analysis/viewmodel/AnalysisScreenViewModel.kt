package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.AnalysisScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

private object AnalysisScreenViewModelConstants {
    const val FULL_PERCENTAGE = 100
}

@HiltViewModel
public class AnalysisScreenViewModel @Inject constructor(
    closeableCoroutineScope: CloseableCoroutineScope,
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel(closeableCoroutineScope) {
    private var allTransactionData: Flow<List<TransactionData>> = getAllTransactionDataFlowUseCase()
    private val validTransactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    )
    private val transactionTypesChipUIData: List<ChipUIData> = validTransactionTypes.map {
        ChipUIData(
            text = it.title,
        )
    }
    private val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    private val oldestTransactionLocalDate: StateFlow<LocalDate?> = allTransactionData.map {
        dateTimeUtil.getLocalDate(
            timestamp = it.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    private val transactionDataMappedByCategory: StateFlow<List<AnalysisListItemData>> = combine(
        allTransactionData,
        selectedTransactionTypeIndex,
        selectedFilter,
    ) { allTransactionDataValue, selectedTransactionTypeIndexValue, selectedFilterValue ->
        getAnalysisListItemData(
            selectedFilterValue = selectedFilterValue,
            selectedTransactionTypeIndexValue = selectedTransactionTypeIndexValue,
            allTransactionDataValue = allTransactionDataValue
        )
    }.defaultListStateIn(
        scope = closeableCoroutineScope,
    )

    public val screenUIData: StateFlow<MyResult<AnalysisScreenUIData>?> = combine(
        selectedFilter,
        selectedTransactionTypeIndex,
        transactionDataMappedByCategory,
        oldestTransactionLocalDate,
    ) {
            selectedFilter,
            selectedTransactionTypeIndex,
            transactionDataMappedByCategory,
            oldestTransactionLocalDate,
        ->
        if (
            oldestTransactionLocalDate.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AnalysisScreenUIData(
                    selectedFilter = selectedFilter,
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    transactionDataMappedByCategory = transactionDataMappedByCategory,
                    transactionTypesChipUIData = transactionTypesChipUIData,
                    currentLocalDate = dateTimeUtil.getCurrentLocalDate(),
                    oldestTransactionLocalDate = oldestTransactionLocalDate.orMin(),
                    startOfMonthLocalDate = dateTimeUtil.getStartOfMonthLocalDate(),
                    startOfYearLocalDate = dateTimeUtil.getStartOfYearLocalDate(),
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    public fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private fun getAnalysisListItemData(
        selectedFilterValue: Filter,
        selectedTransactionTypeIndexValue: Int,
        allTransactionDataValue: List<TransactionData>,
    ): ImmutableList<AnalysisListItemData> {
        val selectedTransactionType = validTransactionTypes[selectedTransactionTypeIndexValue]
        val result = allTransactionDataValue
            .filter { transactionData ->
                transactionData.transaction.transactionType == selectedTransactionType && isAvailableAfterDateFilter(
                    startLocalDate = selectedFilterValue.fromLocalDate,
                    endLocalDate = selectedFilterValue.toLocalDate,
                    transactionData = transactionData,
                )
            }
            .groupBy {
                it.category
            }
            .mapNotNull { (category, transactionDataList) ->
                category?.let {
                    TransactionDataMappedByCategory(
                        category = it,
                        amountValue = transactionDataList.sumOf { transactionData ->
                            transactionData.transaction.amount.value
                        },
                        percentage = 0.0,
                    )
                }
            }
            .sortedByDescending {
                it.amountValue
            }
        val sum = result.sumOf {
            it.amountValue
        }
        return result.map {
            val percentage = (it.amountValue.toDouble() / sum).toFloat()
            AnalysisListItemData(
                amountText = Amount(
                    value = it.amountValue,
                ).toString(),
                emoji = it.category.emoji,
                percentage = percentage,
                percentageText = "%.2f".format((percentage * AnalysisScreenViewModelConstants.FULL_PERCENTAGE))
                    .let { formattedPercentage ->
                        "$formattedPercentage%"
                    },
                title = it.category.title,
            )
        }.toImmutableList()
    }

    private fun isAvailableAfterDateFilter(
        startLocalDate: LocalDate?,
        endLocalDate: LocalDate?,
        transactionData: TransactionData,
    ): Boolean {
        if (startLocalDate.isNull() || endLocalDate.isNull()) {
            return true
        }
        val fromDateStartOfDayTimestamp = startLocalDate
            .atStartOfDay()
            .toEpochMilli()
        val toDateStartOfDayTimestamp = endLocalDate
            .atEndOfDay()
            .toEpochMilli()
        return transactionData.transaction.transactionTimestamp in fromDateStartOfDayTimestamp until toDateStartOfDayTimestamp
    }
}

