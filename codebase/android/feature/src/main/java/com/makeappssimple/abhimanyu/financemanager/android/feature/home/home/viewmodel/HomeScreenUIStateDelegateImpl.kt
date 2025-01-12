package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewTabOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant

private object HomeScreenUIStateDelegateImplConstants {
    const val DEFAULT_OVERVIEW_TAB_SELECTION = 1
}

internal class HomeScreenUIStateDelegateImpl(
    dateTimeKit: DateTimeKit,
    private val navigationKit: NavigationKit,
) : HomeScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val isBalanceVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    override val screenBottomSheetType: MutableStateFlow<HomeScreenBottomSheetType> =
        MutableStateFlow(
            value = HomeScreenBottomSheetType.None,
        )
    override val homeListItemViewData: MutableStateFlow<ImmutableList<TransactionListItemData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    override val overviewTabSelectionIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = HomeScreenUIStateDelegateImplConstants.DEFAULT_OVERVIEW_TAB_SELECTION,
    )
    override val selectedTimestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = dateTimeKit.getCurrentTimeMillis(),
    )
    override val overviewCardData: MutableStateFlow<OverviewCardViewModelData?> = MutableStateFlow(
        value = null,
    )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
        }
    }
    // endregion

    // region state events
    override fun handleOverviewCardAction(
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

    override fun navigateToAccountsScreen() {
        navigationKit.navigateToAccountsScreen()
    }

    override fun navigateToAddTransactionScreen() {
        navigationKit.navigateToAddTransactionScreen()
    }

    override fun navigateToAnalysisScreen() {
        navigationKit.navigateToAnalysisScreen()
    }

    override fun navigateToSettingsScreen() {
        navigationKit.navigateToSettingsScreen()
    }

    override fun navigateToTransactionsScreen() {
        navigationKit.navigateToTransactionsScreen()
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigationKit.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedHomeScreenBottomSheetType = HomeScreenBottomSheetType.None,
        )
    }

    override fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    ) {
        isBalanceVisible.update {
            updatedIsBalanceVisible
        }
    }

    override fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    override fun updateScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedHomeScreenBottomSheetType
        }
    }
    // endregion
}
