package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toZonedDateTime
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
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
    private val dateTimeUtil: DateTimeUtil,
    private val navigator: Navigator,
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
        value = dateTimeUtil.getCurrentTimeMillis(),
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
        navigator.navigateToAccountsScreen()
    }

    override fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    override fun navigateToAnalysisScreen() {
        navigator.navigateToAnalysisScreen()
    }

    override fun navigateToSettingsScreen() {
        navigator.navigateToSettingsScreen()
    }

    override fun navigateToTransactionsScreen() {
        navigator.navigateToTransactionsScreen()
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedHomeScreenBottomSheetType = HomeScreenBottomSheetType.None,
        )
    }

    override fun setBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    ) {
        isBalanceVisible.update {
            updatedIsBalanceVisible
        }
    }

    override fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    ) {
        overviewTabSelectionIndex.value = updatedOverviewTabSelectionIndex
    }

    override fun setScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedHomeScreenBottomSheetType
        }
    }
    // endregion
}
