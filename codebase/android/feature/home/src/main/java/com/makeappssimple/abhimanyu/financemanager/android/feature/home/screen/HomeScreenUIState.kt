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
public class HomeScreenUIState(
    public val isBalanceVisible: Boolean,
    public val screenBottomSheetType: HomeScreenBottomSheetType,
    public val setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean,
    public val isBackupCardVisible: Boolean,
    public val overviewTabSelectionIndex: Int,
    public val transactionListItemDataList: List<TransactionListItemData>,
    public val accountsTotalBalanceAmountValue: Long,
    public val accountsTotalMinimumBalanceAmountValue: Long,
    public val overviewCardData: OverviewCardViewModelData,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setBalanceVisible: (Boolean) -> Unit,
    public val pieChartData: PieChartData,
) : ScreenUIState

@Composable
public fun rememberHomeScreenUIState(
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

        HomeScreenUIState(
            isBalanceVisible = isBalanceVisible,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setBalanceVisible = setBalanceVisible,
            isLoading = unwrappedData.isNull(),
            isBackupCardVisible = unwrappedData?.isBackupCardVisible.orFalse(),
            overviewTabSelectionIndex = unwrappedData?.overviewTabSelectionIndex.orZero(),
            transactionListItemDataList = unwrappedData?.transactionListItemDataList.orEmpty(),
            accountsTotalBalanceAmountValue = unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
            accountsTotalMinimumBalanceAmountValue = unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
            overviewCardData = unwrappedData?.overviewCardData.orDefault(),
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(HomeScreenBottomSheetType.None)
            },
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
        )
    }
}
