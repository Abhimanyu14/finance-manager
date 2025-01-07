package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction.GetAllTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionDataMappedByCategory
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.analysis.AnalysisListItemData
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
public class AnalysisScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllTransactionDataUseCase: GetAllTransactionDataUseCase,
    private val navigator: Navigator,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), AnalysisScreenUIStateDelegate by AnalysisScreenUIStateDelegateImpl(
    navigator = navigator,
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
        setScreenBottomSheetType = ::setScreenBottomSheetType,
        setSelectedFilter = ::setSelectedFilter,
        setSelectedTransactionTypeIndex = ::setSelectedTransactionTypeIndex,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
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
        return dateTimeUtil.getLocalDate(
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
                        defaultEndLocalDate = dateTimeUtil.getCurrentLocalDate(),
                        startOfCurrentMonthLocalDate = dateTimeUtil.getStartOfMonthLocalDate(),
                        startOfCurrentYearLocalDate = dateTimeUtil.getStartOfYearLocalDate(),
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
