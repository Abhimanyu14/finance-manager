package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.AnalysisScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AnalysisScreenViewModelImpl @Inject constructor(
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    override val myLogger: MyLogger,
    override val navigationManager: NavigationManager,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
) : AnalysisScreenViewModel, ViewModel() {
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
        scope = viewModelScope,
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
        scope = viewModelScope,
    )

    override val screenUIData: StateFlow<MyResult<AnalysisScreenUIData>?> = combine(
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
        scope = viewModelScope,
    )

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {

        }
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    override fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    override fun updateSelectedTransactionTypeIndex(
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
    ): List<AnalysisListItemData> {
        val selectedTransactionType = validTransactionTypes[selectedTransactionTypeIndexValue]
        val result = allTransactionDataValue
            .filter { transactionData ->
                transactionData.transaction.transactionType == selectedTransactionType && isAvailableAfterDateFilter(
                    startLocalDate = selectedFilterValue.fromLocalDate,
                    endLocalDate = selectedFilterValue.toLocalDate,
                    transactionData = transactionData,
                )
            }.groupBy {
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
            AnalysisListItemData(
                amountText = Amount(
                    value = it.amountValue,
                ).toString(),
                emoji = it.category.emoji,
                percentage = (it.amountValue.toDouble() / sum).toFloat(),
                percentageText = "%.2f".format(it.percentage).let { percentage ->
                    "$percentage%"
                },
                title = it.category.title,
            )
        }
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
        return transactionData.transaction.transactionTimestamp in (fromDateStartOfDayTimestamp) until toDateStartOfDayTimestamp
    }
}
