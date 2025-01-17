package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private object AnalysisScreenViewModelConstants {
    const val FULL_PERCENTAGE = 100
}

@HiltViewModel
internal class AnalysisScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val dateTimeKit: DateTimeKit,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AnalysisScreenUIStateDelegate by AnalysisScreenUIStateDelegateImpl(
    navigationKit = navigationKit,
) {
    // region initial data
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
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<AnalysisScreenUIState> =
        MutableStateFlow(
            value = AnalysisScreenUIState(),
        )
    internal val uiStateEvents: AnalysisScreenUIStateEvents = AnalysisScreenUIStateEvents(
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setSelectedFilter = ::updateSelectedFilter,
        setSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                allTransactionData = getAllTransactionDataUseCase()
                oldestTransactionLocalDate = getOldestTransactionLocalDate()
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTransactionDataMappedByCategory()
    }
    // endregion

    // region getOldestTransactionLocalDate
    private fun getOldestTransactionLocalDate(): LocalDate {
        return dateTimeKit.getLocalDate(
            timestamp = allTransactionData.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                selectedFilter,
                selectedTransactionTypeIndex,
                analysisListItemData,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        selectedFilter,
                        selectedTransactionTypeIndex,
                        analysisListItemData,
                    ),
                ->
                uiState.update {
                    AnalysisScreenUIState(
                        screenBottomSheetType = screenBottomSheetType,
                        isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                        isLoading = isLoading,
                        selectedFilter = selectedFilter.orEmpty(),
                        selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                        analysisListItemData = analysisListItemData,
                        transactionTypesChipUIData = validTransactionTypesChipUIData,
                        defaultStartLocalDate = oldestTransactionLocalDate.orMin(),
                        defaultEndLocalDate = dateTimeKit.getCurrentLocalDate(),
                        startOfCurrentMonthLocalDate = dateTimeKit.getStartOfMonthLocalDate(),
                        startOfCurrentYearLocalDate = dateTimeKit.getStartOfYearLocalDate(),
                    )
                }
            }
        }
    }
    // endregion

    // region observeForTransactionDataMappedByCategory
    private fun observeForTransactionDataMappedByCategory() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                selectedTransactionTypeIndex,
                selectedFilter,
            ) {
                    (
                        isLoading,
                        selectedTransactionTypeIndex,
                        selectedFilter,
                    ),
                ->
                analysisListItemData.update {
                    getAnalysisListItemData(
                        selectedFilterValue = selectedFilter,
                        selectedTransactionTypeIndexValue = selectedTransactionTypeIndex,
                        allTransactionDataValue = allTransactionData,
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
    // endregion
}
