package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Stable
class HomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
    private val unwrappedData: HomeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    val screenBottomSheetType: HomeScreenBottomSheetType,
    val setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit,
    private val totalIncomeAmount: Amount = Amount(
        value = unwrappedData?.overviewCardData?.income?.toLong().orZero(),
    ),
    private val totalExpenseAmount: Amount = Amount(
        value = unwrappedData?.overviewCardData?.expense?.toLong().orZero(),
    ),
    val isLoading: Boolean = unwrappedData.isNull(),
    val isBackupCardVisible: Boolean = unwrappedData?.isBackupCardVisible.orFalse(),
    val overviewTabSelectionIndex: Int = unwrappedData?.overviewTabSelectionIndex.orZero(),
    val transactionListItemDataList: List<TransactionListItemData> =
        unwrappedData?.transactionListItemDataList.orEmpty(),
    val accountsTotalBalanceAmountValue: Long =
        unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
    val accountsTotalMinimumBalanceAmountValue: Long =
        unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
    val overviewCardData: OverviewCardViewModelData = unwrappedData?.overviewCardData.orDefault(),
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(HomeScreenBottomSheetType.NONE)
    },
    val pieChartData: PieChartData = PieChartData(
        items = listOf(
            PieChartItemData(
                value = unwrappedData?.overviewCardData?.income.orZero(),
                text = "Income : $totalIncomeAmount",
                color = MyColor.TERTIARY,
            ),
            PieChartItemData(
                value = unwrappedData?.overviewCardData?.expense.orZero(),
                text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                color = MyColor.ERROR,
            ),
        ),
    ),
) : ScreenUIState

@Composable
fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIState {
    val (screenBottomSheetType: HomeScreenBottomSheetType, setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = HomeScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        HomeScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
