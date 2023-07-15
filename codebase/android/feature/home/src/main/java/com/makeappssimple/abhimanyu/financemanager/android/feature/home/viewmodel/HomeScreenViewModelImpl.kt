package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionsBetweenTimestampsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
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
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
    override val myLogger: MyLogger,
    private val backupDataUseCase: BackupDataUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val getTransactionsBetweenTimestampsUseCase: GetTransactionsBetweenTimestampsUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationManager: NavigationManager,
) : HomeScreenViewModel, ViewModel() {
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
        val overviewTabOption = OverviewTabOption.values()[overviewTabSelectionIndex]
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
        scope = viewModelScope,
    )

    private val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()

    override val screenUIData: StateFlow<MyResult<HomeScreenUIData>?> = combine(
        isBackupCardVisible,
        homeListItemViewData,
        overviewTabSelectionIndex,
        overviewCardData,
        sourcesTotalBalanceAmountValue,
    ) {
            isBackupCardVisible,
            homeListItemViewData,
            overviewTabSelectionIndex,
            overviewCardData,
            sourcesTotalBalanceAmountValue,
        ->
        if (homeListItemViewData.isNull()) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = HomeScreenUIData(
                    isBackupCardVisible = isBackupCardVisible,
                    overviewTabSelectionIndex = overviewTabSelectionIndex,
                    transactionListItemDataList = homeListItemViewData,
                    sourcesTotalBalanceAmountValue = sourcesTotalBalanceAmountValue,
                    overviewCardData = overviewCardData,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    override fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    ) {
        val overviewTabOption = OverviewTabOption.values()[overviewTabSelectionIndex.value]
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

    override fun navigateToAnalysisScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Analysis
        )
    }

    override fun navigateToAddTransactionScreen() {
        navigationManager.navigate(
            MyNavigationDirections.AddTransaction()
        )
    }

    override fun navigateToSettingsScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Settings
        )
    }

    override fun navigateToSourcesScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Sources
        )
    }

    override fun navigateToTransactionsScreen() {
        navigationManager.navigate(
            MyNavigationDirections.Transactions
        )
    }

    override fun setOverviewTabSelectionIndex(
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
                            isPositive = listItem.sourceTo.isNotNull(),
                            isNegative = listItem.sourceFrom.isNotNull(),
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
                val sourceFromName = listItem.sourceFrom?.name
                val sourceToName = listItem.sourceTo?.name
                val title: String = listItem.transaction.title
                val transactionForText: String =
                    listItem.transactionFor.titleToDisplay

                TransactionListItemData(
                    transactionId = listItem.transaction.id,
                    amountColor = amountColor,
                    amountText = amountText,
                    dateAndTimeText = dateAndTimeText,
                    emoji = emoji,
                    sourceFromName = sourceFromName,
                    sourceToName = sourceToName,
                    title = title,
                    transactionForText = transactionForText,
                )
            }
        }
    }

    private fun getIsBackupCardVisibleFromData(): Flow<Boolean> {
        return myPreferencesRepository.getDataTimestamp().map {
            it.isNotNull() && it.lastBackup < it.lastChange
        }
    }
}
