package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet.HomeScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface HomeScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val isBalanceVisible: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<HomeScreenBottomSheetType>
    val homeListItemViewData: MutableStateFlow<ImmutableList<TransactionListItemData>>
    val overviewTabSelectionIndex: MutableStateFlow<Int>
    val selectedTimestamp: MutableStateFlow<Long>
    val overviewCardData: MutableStateFlow<OverviewCardViewModelData?>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    )

    fun navigateToAccountsScreen()

    fun navigateToAddTransactionScreen()

    fun navigateToAnalysisScreen()

    fun navigateToSettingsScreen()

    fun navigateToTransactionsScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun resetScreenBottomSheetType()

    fun updateIsBalanceVisible(
        updatedIsBalanceVisible: Boolean,
    )

    fun updateOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )

    fun updateScreenBottomSheetType(
        updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType,
    )
    // endregion
}
