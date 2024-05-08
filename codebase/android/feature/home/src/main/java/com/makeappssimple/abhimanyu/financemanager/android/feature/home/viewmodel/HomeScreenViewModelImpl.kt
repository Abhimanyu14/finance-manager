package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import kotlin.math.abs

@VisibleForTesting
internal const val defaultOverviewTabSelection = 1

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase,
    getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase,
    private val backupDataUseCase: BackupDataUseCase,
    private val closeableCoroutineScope: CloseableCoroutineScope,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : HomeScreenViewModel, ViewModel(closeableCoroutineScope) {
    private val homeListItemViewData: Flow<List<TransactionListItemData>> =
        getHomeListItemViewDataFromData()
    private val isBackupCardVisible: Flow<Boolean> = getIsBackupCardVisibleFromData()

    private val overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = defaultOverviewTabSelection,
    )

    private val timestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeUtil.getCurrentTimeMillis(),
    )

    private val overviewCardData: StateFlow<OverviewCardViewModelData?> = combine(
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
        val expenseTransactionsWithRefund = buildList {
            expenseTransactions.forEach { expenseTransaction ->
                add(expenseTransaction)
                expenseTransaction.refundTransactionIds?.let { refundTransactionIds ->
                    refundTransactionIds.forEach { id ->
                        getTransactionUseCase(id)?.let {
                            add(it)
                        }
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
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    private val accountsTotalBalanceAmountValue: Flow<Long> =
        getAccountsTotalBalanceAmountValueUseCase()
    private val accountsTotalMinimumBalanceAmountValue: Flow<Long> =
        getAccountsTotalMinimumBalanceAmountValueUseCase()

    override val screenUIData: StateFlow<MyResult<HomeScreenUIData>?> = combine(
        isBackupCardVisible,
        homeListItemViewData,
        overviewTabSelectionIndex,
        overviewCardData,
        accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue,
    ) { flows ->
        val isBackupCardVisible = flows[0] as? Boolean
        val homeListItemViewData =
            (flows[1] as? List<*>)?.filterIsInstance<TransactionListItemData>()
        val overviewTabSelectionIndex = flows[2] as? Int
        val overviewCardData = flows[3] as? OverviewCardViewModelData
        val accountsTotalBalanceAmountValue = flows[4] as? Long
        val accountsTotalMinimumBalanceAmountValue = flows[5] as? Long

        if (
            isBackupCardVisible.isNull() ||
            homeListItemViewData.isNull() ||
            overviewTabSelectionIndex.isNull() ||
            accountsTotalBalanceAmountValue.isNull() ||
            accountsTotalMinimumBalanceAmountValue.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = HomeScreenUIData(
                    isBackupCardVisible = isBackupCardVisible,
                    overviewTabSelectionIndex = overviewTabSelectionIndex,
                    transactionListItemDataList = homeListItemViewData,
                    accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue,
                    accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue,
                    overviewCardData = overviewCardData,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = closeableCoroutineScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        closeableCoroutineScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigator.navigateUp()
        }
    }

    override fun handleUIEvent(
        uiEvent: HomeScreenUIEvent,
    ) {
        when (uiEvent) {
            is HomeScreenUIEvent.OnTotalBalanceCardClick -> {
                navigateToAccountsScreen()
            }

            is HomeScreenUIEvent.OnFloatingActionButtonClick -> {
                navigateToAddTransactionScreen()
            }

            is HomeScreenUIEvent.OnOverviewCard.Click -> {
                navigateToAnalysisScreen()
            }

            is HomeScreenUIEvent.OnTopAppBarSettingsButtonClick -> {
                navigateToSettingsScreen()
            }

            is HomeScreenUIEvent.OnHomeRecentTransactionsClick -> {
                navigateToTransactionsScreen()
            }

            is HomeScreenUIEvent.OnTransactionListItemClick -> {
                navigateToViewTransactionScreen(
                    transactionId = uiEvent.transactionId,
                )
            }

            is HomeScreenUIEvent.OnOverviewCard.Action -> {
                handleOverviewCardAction(
                    overviewCardAction = uiEvent.overviewCardAction,
                )
            }

            is HomeScreenUIEvent.OnOverviewCard.TabClick -> {
                setOverviewTabSelectionIndex(
                    updatedOverviewTabSelectionIndex = uiEvent.index,
                )
            }

            else -> {
                // Noop, should have been handled in Screen composable or invalid event
            }
        }
    }

    private fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ) {
        val overviewTabOption = OverviewTabOption.entries[overviewTabSelectionIndex.value]
        when (overviewCardAction) {
            OverviewCardAction.NEXT -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .plusYears(1)
                            .toEpochMilli()
                    }
                }
            }

            OverviewCardAction.PREV -> {
                when (overviewTabOption) {
                    OverviewTabOption.DAY -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusDays(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.MONTH -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusMonths(1)
                            .toEpochMilli()
                    }

                    OverviewTabOption.YEAR -> {
                        timestamp.value = Instant
                            .ofEpochMilli(timestamp.value)
                            .toZonedDateTime()
                            .minusYears(1)
                            .toEpochMilli()
                    }
                }
            }
        }
    }

    private fun navigateToAnalysisScreen() {
        navigator.navigateToAnalysisScreen()
    }

    private fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    private fun navigateToSettingsScreen() {
        navigator.navigateToSettingsScreen()
    }

    private fun navigateToAccountsScreen() {
        navigator.navigateToAccountsScreen()
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

    private fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    private fun getHomeListItemViewDataFromData(): Flow<List<TransactionListItemData>> {
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
        return myPreferencesRepository.getDataTimestamp().map {
            it.isNotNull() && (it.lastBackup < it.lastChange)
        }
    }
}
