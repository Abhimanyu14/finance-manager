package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import androidx.compose.material3.MaterialTheme
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
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardViewModelData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData

@Stable
class HomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
    val homeBottomSheetType: HomeBottomSheetType,
    val setHomeBottomSheetType: (HomeBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    private val totalIncomeAmount = Amount(
        value = unwrappedData?.overviewCardData?.income?.toLong().orZero(),
    )
    private val totalExpenseAmount = Amount(
        value = unwrappedData?.overviewCardData?.expense?.toLong().orZero(),
    )

    val isLoading: Boolean = unwrappedData.isNull()
    val isBackupCardVisible: Boolean = unwrappedData?.isBackupCardVisible.orFalse()
    val overviewTabSelectionIndex: Int = unwrappedData?.overviewTabSelectionIndex.orZero()
    val transactionListItemDataList: List<TransactionListItemData> =
        unwrappedData?.transactionListItemDataList.orEmpty()
    val sourcesTotalBalanceAmountValue: Long =
        unwrappedData?.sourcesTotalBalanceAmountValue.orZero()
    val overviewCardData: OverviewCardViewModelData = unwrappedData?.overviewCardData.orDefault()
    val pieChartData
        @Composable get() = PieChartData(
            items = listOf(
                PieChartItemData(
                    value = unwrappedData?.overviewCardData?.income.orZero(),
                    text = "Income : $totalIncomeAmount",
                    color = MaterialTheme.colorScheme.tertiary,
                ),
                PieChartItemData(
                    value = unwrappedData?.overviewCardData?.income.orZero(),
                    text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                    color = MaterialTheme.colorScheme.error,
                ),
            ),
        )
    val resetBottomSheetType: () -> Unit = {
        setHomeBottomSheetType(HomeBottomSheetType.NONE)
    }
}

@Composable
fun rememberHomeScreenUIState(
    data: MyResult<HomeScreenUIData>?,
): HomeScreenUIState {
    val (homeBottomSheetType: HomeBottomSheetType, setHomeBottomSheetType: (HomeBottomSheetType) -> Unit) = remember {
        mutableStateOf(
            value = HomeBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        homeBottomSheetType,
        setHomeBottomSheetType,
    ) {
        HomeScreenUIState(
            data = data,
            homeBottomSheetType = homeBottomSheetType,
            setHomeBottomSheetType = setHomeBottomSheetType,
        )
    }
}
