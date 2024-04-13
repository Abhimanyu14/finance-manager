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
public class HomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
    private val unwrappedData: HomeScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    public val isBalanceVisible: Boolean,
    public val screenBottomSheetType: HomeScreenBottomSheetType,
    public val setScreenBottomSheetType: (HomeScreenBottomSheetType) -> Unit,
    private val totalIncomeAmount: Amount = Amount(
        value = unwrappedData?.overviewCardData?.income?.toLong().orZero(),
    ),
    private val totalExpenseAmount: Amount = Amount(
        value = unwrappedData?.overviewCardData?.expense?.toLong().orZero(),
    ),
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val isBackupCardVisible: Boolean = unwrappedData?.isBackupCardVisible.orFalse(),
    public val overviewTabSelectionIndex: Int = unwrappedData?.overviewTabSelectionIndex.orZero(),
    public val transactionListItemDataList: List<TransactionListItemData> =
        unwrappedData?.transactionListItemDataList.orEmpty(),
    public val accountsTotalBalanceAmountValue: Long =
        unwrappedData?.accountsTotalBalanceAmountValue.orZero(),
    public val accountsTotalMinimumBalanceAmountValue: Long =
        unwrappedData?.accountsTotalMinimumBalanceAmountValue.orZero(),
    public val overviewCardData: OverviewCardViewModelData = unwrappedData?.overviewCardData.orDefault(),
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(HomeScreenBottomSheetType.NONE)
    },
    public val setBalanceVisible: (Boolean) -> Unit,
    public val pieChartData: PieChartData = PieChartData(
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
public fun rememberHomeScreenUIState(
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
