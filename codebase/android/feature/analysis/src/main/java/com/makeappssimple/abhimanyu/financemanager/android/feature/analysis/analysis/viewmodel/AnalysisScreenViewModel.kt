package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quintuple
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private object AnalysisScreenViewModelConstants {
    const val FULL_PERCENTAGE = 100
}

@HiltViewModel
public class AnalysisScreenViewModel @Inject constructor(
    private val dateTimeUtil: DateTimeUtil,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val validTransactionTypes: ImmutableList<TransactionType> = persistentListOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
        TransactionType.INVESTMENT,
    )
    private val validTransactionTypesChipUIData: ImmutableList<ChipUIData> =
        validTransactionTypes.map {
            ChipUIData(
                text = it.title,
            )
        }
    private val analysisListItemData: MutableStateFlow<ImmutableList<AnalysisListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )

    private var allTransactionData: ImmutableList<TransactionData> = persistentListOf()
    private var oldestTransactionLocalDate: LocalDate? = null

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    private val screenBottomSheetType: MutableStateFlow<AnalysisScreenBottomSheetType> =
        MutableStateFlow(
            value = AnalysisScreenBottomSheetType.None,
        )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<AnalysisScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AnalysisScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            allTransactionData = getAllTransactionDataUseCase()
            oldestTransactionLocalDate = getOldestTransactionLocalDate()
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForTransactionDataMappedByCategoryChanges()
        observeForUiStateAndStateEventsChanges()
    }

    private fun getOldestTransactionLocalDate(): LocalDate {
        return dateTimeUtil.getLocalDate(
            timestamp = allTransactionData.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }

    private fun observeForTransactionDataMappedByCategoryChanges() {
        viewModelScope.launch {
            combine(
                isLoading,
                selectedTransactionTypeIndex,
                selectedFilter,
            ) { _, selectedTransactionTypeIndexValue, selectedFilterValue ->
                Pair(selectedTransactionTypeIndexValue, selectedFilterValue)
            }.collectLatest { (selectedTransactionTypeIndexValue, selectedFilterValue) ->
                analysisListItemData.update {
                    getAnalysisListItemData(
                        selectedFilterValue = selectedFilterValue,
                        selectedTransactionTypeIndexValue = selectedTransactionTypeIndexValue,
                        allTransactionDataValue = allTransactionData,
                    )
                }
            }
        }
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combine(
                isLoading,
                screenBottomSheetType,
                selectedFilter,
                selectedTransactionTypeIndex,
                analysisListItemData,
            ) {
                    isLoading,
                    screenBottomSheetType,
                    selectedFilter,
                    selectedTransactionTypeIndex,
                    analysisListItemData,
                ->
                Quintuple(
                    isLoading,
                    screenBottomSheetType,
                    selectedFilter,
                    selectedTransactionTypeIndex,
                    analysisListItemData,
                )
            }.collectLatest {
                    (
                        isLoading,
                        screenBottomSheetType,
                        selectedFilter,
                        selectedTransactionTypeIndex,
                        analysisListItemData,
                    ),
                ->
                uiStateAndStateEvents.update {
                    AnalysisScreenUIStateAndStateEvents(
                        state = AnalysisScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                            isLoading = isLoading,
                            selectedFilter = selectedFilter.orEmpty(),
                            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                            analysisListItemData = analysisListItemData,
                            transactionTypesChipUIData = validTransactionTypesChipUIData,
                            defaultStartLocalDate = oldestTransactionLocalDate.orMin(),
                            defaultEndLocalDate = dateTimeUtil.getCurrentLocalDate(),
                            startOfCurrentMonthLocalDate = dateTimeUtil.getStartOfMonthLocalDate(),
                            startOfCurrentYearLocalDate = dateTimeUtil.getStartOfYearLocalDate(),
                        ),
                        events = AnalysisScreenUIStateEvents(
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSelectedFilter = ::setSelectedFilter,
                            setSelectedTransactionTypeIndex = ::setSelectedTransactionTypeIndex,
                        ),
                    )
                }
            }
        }
    }

    private fun getAnalysisListItemData(
        selectedFilterValue: Filter,
        selectedTransactionTypeIndexValue: Int,
        allTransactionDataValue: ImmutableList<TransactionData>,
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
        return transactionData.transaction.transactionTimestamp in fromDateStartOfDayTimestamp until toDateStartOfDayTimestamp
    }

    // region state events
    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAnalysisScreenBottomSheetType
        }
    }

    private fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    private fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }
    // endregion
}
