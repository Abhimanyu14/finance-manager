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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault

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
    val isBalanceVisible: Boolean,
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
    val setBalanceVisible: (Boolean) -> Unit,
    val pieChartData: PieChartData = PieChartData(
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
) : ScreenUIState

@Composable
fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIState {
    val (isBalanceVisible: Boolean, setBalanceVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    val (screenBottomSheetType: HomeScreenBottomSheetType, setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = HomeScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isBalanceVisible,
        screenBottomSheetType,
        setBalanceVisible,
        setScreenBottomSheetType,
    ) {
        HomeScreenUIState(
            data = data,
            isBalanceVisible = isBalanceVisible,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setBalanceVisible = setBalanceVisible,
        )
    }
}
