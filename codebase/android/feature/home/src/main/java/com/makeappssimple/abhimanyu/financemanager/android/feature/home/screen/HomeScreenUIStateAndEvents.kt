package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toNonSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault

@Stable
internal class HomeScreenUIStateAndEvents(
    val state: HomeScreenUIState,
    val events: HomeScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberHomeScreenUIStateAndEvents(
    overviewCardData: OverviewCardViewModelData?,
    homeListItemViewData: List<TransactionListItemData>,
    isBackupCardVisible: Boolean,
    overviewTabSelectionIndex: Int,
    accountsTotalBalanceAmountValue: Long,
    accountsTotalMinimumBalanceAmountValue: Long,
): HomeScreenUIStateAndEvents {
    // region is balance visible
    val (isBalanceVisible: Boolean, setBalanceVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region bottom sheet type
    var screenBottomSheetType: HomeScreenBottomSheetType by remember {
        mutableStateOf(
            value = HomeScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType ->
            screenBottomSheetType = updatedHomeScreenBottomSheetType
        }
    // endregion

    return remember(
        isBalanceVisible,
        setBalanceVisible,
        screenBottomSheetType,
        setScreenBottomSheetType,
        overviewCardData,
        homeListItemViewData,
        isBackupCardVisible,
        overviewTabSelectionIndex,
        accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue,
    ) {
        val totalIncomeAmount = Amount(
            value = overviewCardData?.income?.toLong().orZero(),
        )
        val totalExpenseAmount = Amount(
            value = overviewCardData?.expense?.toLong().orZero(),
        )

        // TODO(Abhi): Can be reordered to match the class ordering
        HomeScreenUIStateAndEvents(
            HomeScreenUIState(
                isBackupCardVisible = isBackupCardVisible.orFalse(),
                isBalanceVisible = isBalanceVisible,
                isLoading = false,
                isRecentTransactionsTrailingTextVisible = homeListItemViewData
                    .isNotEmpty(),
                screenBottomSheetType = screenBottomSheetType,
                overviewTabSelectionIndex = overviewTabSelectionIndex.orZero(),
                transactionListItemDataList = homeListItemViewData,
                accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.orZero(),
                overviewCardData = overviewCardData.orDefault(),
                pieChartData = PieChartData(
                    items = listOf(
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
            HomeScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(HomeScreenBottomSheetType.None)
                },
                setBalanceVisible = setBalanceVisible,
            )
        )
    }
}
