package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault

@Stable
internal class HomeScreenUIStateAndEvents(
    val state: HomeScreenUIState,
    val events: HomeScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class HomeScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setBalanceVisible: (Boolean) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberHomeScreenUIStateAndEvents(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIStateAndEvents {
    val (isBalanceVisible: Boolean, setBalanceVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    var screenBottomSheetType: HomeScreenBottomSheetType by remember {
        mutableStateOf(
            value = HomeScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedHomeScreenBottomSheetType: HomeScreenBottomSheetType ->
            screenBottomSheetType = updatedHomeScreenBottomSheetType
        }

    return remember(
        data,
        isBalanceVisible,
        screenBottomSheetType,
        setBalanceVisible,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: HomeScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val totalIncomeAmount = Amount(
            value = unwrappedData?.overviewCardData?.income?.toLong().orZero(),
        )
        val totalExpenseAmount = Amount(
            value = unwrappedData?.overviewCardData?.expense?.toLong().orZero(),
        )

        // TODO(Abhi): Can be reordered to match the class ordering
        HomeScreenUIStateAndEvents(
            HomeScreenUIState(
                isBackupCardVisible = unwrappedData?.isBackupCardVisible.orFalse(),
                isBalanceVisible = isBalanceVisible,
                isLoading = unwrappedData.isNull(),
                isRecentTransactionsTrailingTextVisible = unwrappedData?.transactionListItemDataList.orEmpty()
                    .isNotEmpty(),
                screenBottomSheetType = screenBottomSheetType,
                overviewTabSelectionIndex = unwrappedData?.overviewTabSelectionIndex.orZero(),
                transactionListItemDataList = unwrappedData?.transactionListItemDataList.orEmpty(),
                accountsTotalBalanceAmountValue = unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
                overviewCardData = unwrappedData?.overviewCardData.orDefault(),
                pieChartData = PieChartData(
                    items = listOf(
                        PieChartItemData(
                            value = unwrappedData?.overviewCardData?.income.orZero(),
                            text = "Income : $totalIncomeAmount", // TODO(Abhi): Move to string resources
                            color = MyColor.TERTIARY,
                        ),
                        PieChartItemData(
                            value = unwrappedData?.overviewCardData?.expense.orZero(),
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
