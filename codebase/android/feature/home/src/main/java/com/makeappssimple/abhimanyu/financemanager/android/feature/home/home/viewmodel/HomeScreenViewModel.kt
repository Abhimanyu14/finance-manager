package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toNonSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.state.HomeScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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
    private val backupDataUseCase: BackupDataUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val homeListItemViewData: Flow<ImmutableList<TransactionListItemData>> =
        getHomeListItemViewDataFromData()
    private val isBackupCardVisible: Flow<Boolean> = getIsBackupCardVisibleFromData()

    private val overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = HomeScreenViewModelConstants.DEFAULT_OVERVIEW_TAB_SELECTION,
    )

    private val timestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeUtil.getCurrentTimeMillis(),
    )

    private val overviewCardData: Flow<OverviewCardViewModelData?> = combine(
        flow = overviewTabSelectionIndex,
        flow2 = timestamp,
    ) { overviewTabSelectionIndex, timestamp ->
        val overviewTabOption = OverviewTabOption.entries[overviewTabSelectionIndex]
        val transactions = getTransactionsBetweenTimestampsUseCase(
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

        val incomeAmount = transactions.filter {
            it.transactionType == TransactionType.INCOME
        }.sumOf {
            it.amount.value
        }.toFloat()

        val expenseTransactions = transactions.filter {
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

        val title = when (overviewTabOption) {
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

        OverviewCardViewModelData(
            income = incomeAmount,
            expense = expenseAmount,
            title = title,
        )
    }

    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()

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

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }
    // endregion

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

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
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
                            isBackupCardVisible = isBackupCardVisible.orFalse(),
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

    private fun getHomeListItemViewDataFromData(): Flow<ImmutableList<TransactionListItemData>> {
        return getRecentTransactionDataFlowUseCase().map {
            it.map { listItem ->
                val amountColor: MyColor = listItem.transaction.getAmountTextColor()
                val amountText: String =
                    if (listItem.transaction.transactionType == TransactionType.INCOME ||
                        listItem.transaction.transactionType == TransactionType.EXPENSE ||
                        listItem.transaction.transactionType == TransactionType.ADJUSTMENT ||
                        listItem.transaction.transactionType == TransactionType.REFUND
                    ) {
                        listItem.transaction.amount.toSignedString(
                            isPositive = listItem.accountTo.isNotNull(),
                            isNegative = listItem.accountFrom.isNotNull(),
                        )
                    } else {
                        listItem.transaction.amount.toString()
                    }
                val dateAndTimeText: String = dateTimeUtil.getReadableDateAndTime(
                    timestamp = listItem.transaction.transactionTimestamp,
                )
                val emoji: String = when (listItem.transaction.transactionType) {
                    TransactionType.TRANSFER -> {
                        EmojiConstants.LEFT_RIGHT_ARROW
                    }

                    TransactionType.ADJUSTMENT -> {
                        EmojiConstants.EXPRESSIONLESS_FACE
                    }

                    else -> {
                        listItem.category?.emoji.orEmpty()
                    }
                }
                val accountFromName = listItem.accountFrom?.name
                val accountToName = listItem.accountTo?.name
                val title: String = listItem.transaction.title
                val transactionForText: String =
                    listItem.transactionFor.titleToDisplay

                TransactionListItemData(
                    transactionId = listItem.transaction.id,
                    amountColor = amountColor,
                    amountText = amountText,
                    dateAndTimeText = dateAndTimeText,
                    emoji = emoji,
                    accountFromName = accountFromName,
                    accountToName = accountToName,
                    title = title,
                    transactionForText = transactionForText,
                )
            }
        }
    }

    private fun getIsBackupCardVisibleFromData(): Flow<Boolean> {
        return myPreferencesRepository.getDataTimestampFlow().map {
            it.isNotNull() && (it.lastBackup < it.lastChange)
        }
    }

    // region state events
    private fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ) {
        val overviewTabOption = OverviewTabOption.entries[overviewTabSelectionIndex.value]
        when (overviewCardAction) {
            OverviewCardAction.NEXT -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusYears(1)
                            .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant.ofEpochMilli(timestamp.value)
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
