package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.ShouldShowBackupCardUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toNonSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import kotlin.math.abs

private object HomeScreenViewModelConstants {
    const val DEFAULT_OVERVIEW_TAB_SELECTION = 1
}

@HiltViewModel
public class HomeScreenViewModel @Inject constructor(
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    shouldShowBackupCardUseCase: ShouldShowBackupCardUseCase,
    private val backupDataUseCase: BackupDataUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private val isBackupCardVisible: Flow<Boolean> = shouldShowBackupCardUseCase()
    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val isBalanceVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    private val screenBottomSheetType: MutableStateFlow<HomeScreenBottomSheetType> =
        MutableStateFlow(
            value = HomeScreenBottomSheetType.None,
        )
    private val homeListItemViewData: MutableStateFlow<ImmutableList<TransactionListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = HomeScreenViewModelConstants.DEFAULT_OVERVIEW_TAB_SELECTION,
    )
    private val selectedTimestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeUtil.getCurrentTimeMillis(),
    )
    private val overviewCardData: MutableStateFlow<OverviewCardViewModelData?> = MutableStateFlow(
        value = null,
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<HomeScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = HomeScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {}

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForHomeListItemViewData()
        observeForOverviewCardData()
    }
    // endregion

    // region backupDataToDocument
    internal fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch {
            // TODO(Abhi): Change this logic to ensure that this coroutine lives till the backup is completed.
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigator.navigateUp()
        }
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                isBalanceVisible,
                isBackupCardVisible,
                overviewCardData,
                homeListItemViewData,
                overviewTabSelectionIndex,
                accountsTotalBalanceAmountValue,
                accountsTotalMinimumBalanceAmountValue,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        isBalanceVisible,
                        isBackupCardVisible,
                        overviewCardData,
                        homeListItemViewData,
                        overviewTabSelectionIndex,
                        accountsTotalBalanceAmountValue,
                        accountsTotalMinimumBalanceAmountValue,
                    ),
                ->
                val totalIncomeAmount = Amount(
                    value = overviewCardData?.income?.toLong().orZero(),
                )
                val totalExpenseAmount = Amount(
                    value = overviewCardData?.expense?.toLong().orZero(),
                )

                uiStateAndStateEvents.update {
                    HomeScreenUIStateAndStateEvents(
                        state = HomeScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != HomeScreenBottomSheetType.None,
                            isBackupCardVisible = isBackupCardVisible,
                            isBalanceVisible = isBalanceVisible,
                            isLoading = isLoading,
                            isRecentTransactionsTrailingTextVisible = homeListItemViewData
                                .isNotEmpty(),
                            screenBottomSheetType = screenBottomSheetType,
                            overviewTabSelectionIndex = overviewTabSelectionIndex.orZero(),
                            transactionListItemDataList = homeListItemViewData,
                            accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.orZero(),
                            accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.orZero(),
                            overviewCardData = overviewCardData.orDefault(),
                            pieChartData = PieChartData(
                                items = persistentListOf(
                                    PieChartItemData(
                                        value = overviewCardData?.income.orZero(),
                                        text = "Income : $totalIncomeAmount", // TODO(Abhi): Move to string resources
                                        color = MyColor.TERTIARY,
                                    ),
                                    PieChartItemData(
                                        value = overviewCardData?.expense.orZero(),
                                        text = "Expense : ${totalExpenseAmount.toNonSignedString()}", // TODO(Abhi): Move to string resources
                                        color = MyColor.ERROR,
                                    ),
                                ),
                            ),
                        ),
                        events = HomeScreenUIStateEvents(
                            handleOverviewCardAction = ::handleOverviewCardAction,
                            navigateToAccountsScreen = ::navigateToAccountsScreen,
                            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
                            navigateToAnalysisScreen = ::navigateToAnalysisScreen,
                            navigateToSettingsScreen = ::navigateToSettingsScreen,
                            navigateToTransactionsScreen = ::navigateToTransactionsScreen,
                            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setBalanceVisible = ::setBalanceVisible,
                            setOverviewTabSelectionIndex = ::setOverviewTabSelectionIndex,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                        ),
                    )
                }
            }
        }
    }
    // endregion

    // region observeForHomeListItemViewData
    private fun observeForHomeListItemViewData() {
        viewModelScope.launch {
            getRecentTransactionDataFlowUseCase().collectLatest { transactionDataList ->
                startLoading()
                homeListItemViewData.update {
                    transactionDataList.map { transactionData: TransactionData ->
                        transactionData.toTransactionListItemData(
                            dateTimeUtil = dateTimeUtil,
                        )
                    }
                }
                completeLoading()
            }
        }
    }
    // endregion

    // region observeForOverviewCardData
    private fun observeForOverviewCardData() {
        viewModelScope.launch {
            combineAndCollectLatest(
                overviewTabSelectionIndex,
                selectedTimestamp,
            ) { (overviewTabSelectionIndex, timestamp) ->
                val overviewTabOption = OverviewTabOption.entries[overviewTabSelectionIndex]
                val transactionsInSelectedTimeRange = getTransactionsInSelectedTimeRange(
                    overviewTabOption = overviewTabOption,
                    timestamp = timestamp,
                )

                overviewCardData.update {
                    OverviewCardViewModelData(
                        income = getIncomeAmount(
                            transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
                        ),
                        expense = getExpenseAmount(
                            transactionsInSelectedTimeRange = transactionsInSelectedTimeRange,
                        ),
                        title = getTitle(
                            overviewTabOption = overviewTabOption,
                            timestamp = timestamp
                        ),
                    )
                }
            }
        }
    }

    private suspend fun getTransactionsInSelectedTimeRange(
        overviewTabOption: OverviewTabOption,
        timestamp: Long,
    ): ImmutableList<Transaction> {
        return getTransactionsBetweenTimestampsUseCase(
            startingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeUtil.getStartOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeUtil.getStartOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeUtil.getStartOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
            endingTimestamp = when (overviewTabOption) {
                OverviewTabOption.DAY -> {
                    dateTimeUtil.getEndOfDayTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.MONTH -> {
                    dateTimeUtil.getEndOfMonthTimestamp(
                        timestamp = timestamp,
                    )
                }

                OverviewTabOption.YEAR -> {
                    dateTimeUtil.getEndOfYearTimestamp(
                        timestamp = timestamp,
                    )
                }
            },
        )
    }

    private fun getIncomeAmount(
        transactionsInSelectedTimeRange: ImmutableList<Transaction>,
    ): Float {
        return transactionsInSelectedTimeRange.filter {
            it.transactionType == TransactionType.INCOME
        }.sumOf {
            it.amount.value
        }.toFloat()
    }

    private suspend fun getExpenseAmount(
        transactionsInSelectedTimeRange: ImmutableList<Transaction>,
    ): Float {
        val expenseTransactions = transactionsInSelectedTimeRange.filter {
            it.transactionType == TransactionType.EXPENSE
        }
        val expenseTransactionsWithRefund = mutableListOf<Transaction>()
        expenseTransactions.forEach { expenseTransaction ->
            expenseTransactionsWithRefund.add(expenseTransaction)
            expenseTransaction.refundTransactionIds?.let { refundTransactionIds ->
                refundTransactionIds.forEach { id ->
                    getTransactionUseCase(id)?.let {
                        expenseTransactionsWithRefund.add(it)
                    }
                }
            }
        }
        val expenseAmount = expenseTransactionsWithRefund.sumOf { transaction ->
            if (transaction.transactionType == TransactionType.EXPENSE) {
                abs(transaction.amount.value)
            } else {
                -abs(transaction.amount.value)
            }
        }.toFloat()
        return expenseAmount
    }

    private fun getTitle(
        overviewTabOption: OverviewTabOption,
        timestamp: Long,
    ): String {
        return when (overviewTabOption) {
            OverviewTabOption.DAY -> {
                dateTimeUtil.getFormattedDate(timestamp).uppercase()
            }

            OverviewTabOption.MONTH -> {
                dateTimeUtil.getFormattedMonth(timestamp).uppercase()
            }

            OverviewTabOption.YEAR -> {
                dateTimeUtil.getFormattedYear(timestamp).uppercase()
            }
        }
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ) {
        val overviewTabOption = OverviewTabOption.entries[overviewTabSelectionIndex.value]
        when (overviewCardAction) {
            OverviewCardAction.NEXT -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .plusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .plusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .plusYears(1)
                            .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .minusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .minusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        selectedTimestamp.value = Instant.ofEpochMilli(selectedTimestamp.value)
                            .toZonedDateTime()
                            .minusYears(1)
                            .toEpochMilli()
                    }
                }
            }
        }
    }

    private fun navigateToAccountsScreen() {
        navigator.navigateToAccountsScreen()
    }

    private fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    private fun navigateToAnalysisScreen() {
        navigator.navigateToAnalysisScreen()
    }

    private fun navigateToSettingsScreen() {
        navigator.navigateToSettingsScreen()
    }

    private fun navigateToTransactionsScreen() {
        navigator.navigateToTransactionsScreen()
    }

    private fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedHomeScreenBottomSheetType = HomeScreenBottomSheetType.None,
        )
    }

    private fun setBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    ) {
        isBalanceVisible.update {
            updatedIsBalanceVisible
        }
    }

    private fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    private fun setScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedHomeScreenBottomSheetType
        }
    }
    // endregion
}
