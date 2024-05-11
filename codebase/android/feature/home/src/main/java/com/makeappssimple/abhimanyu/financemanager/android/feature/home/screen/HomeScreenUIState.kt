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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault

@Stable
internal class HomeScreenUIState(
    val isBackupCardVisible: Boolean,
    val isBalanceVisible: Boolean,
    val isLoading: Boolean,
    val isRecentTransactionsTrailingTextVisible: Boolean,
    val screenBottomSheetType: HomeScreenBottomSheetType,
    val overviewTabSelectionIndex: Int,
    val transactionListItemDataList: List<TransactionListItemData>,
    val accountsTotalBalanceAmountValue: Long,
    val accountsTotalMinimumBalanceAmountValue: Long,
    val overviewCardData: OverviewCardViewModelData,
    val pieChartData: PieChartData,
    val resetScreenBottomSheetType: () -> Unit,
    val setBalanceVisible: (Boolean) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIState {
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
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(HomeScreenBottomSheetType.None)
            },
            setBalanceVisible = setBalanceVisible,
        )
    }
}
