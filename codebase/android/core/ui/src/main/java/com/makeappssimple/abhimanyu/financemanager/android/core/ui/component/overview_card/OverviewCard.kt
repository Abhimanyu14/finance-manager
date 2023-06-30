package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount

@Composable
fun OverviewCard(
    viewModel: OverviewCardViewModel = hiltViewModel<OverviewCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
    val overviewTabSelectionIndex by viewModel.overviewTabSelectionIndex.collectAsStateWithLifecycle()
    val overviewCardData by viewModel.overviewCardData.collectAsStateWithLifecycle()
    val totalIncomeAmount = Amount(
        value = overviewCardData?.income?.toLong().orZero(),
    )
    val totalExpenseAmount = Amount(
        value = overviewCardData?.expense?.toLong().orZero(),
    )
    val pieChartData = PieChartData(
        items = listOf(
            PieChartItemData(
                value = overviewCardData?.income.orZero(),
                text = "Income : $totalIncomeAmount",
                color = MaterialTheme.colorScheme.tertiary,
            ),
            PieChartItemData(
                value = overviewCardData?.expense.orZero(),
                text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                color = MaterialTheme.colorScheme.error,
            ),
        ),
    )

    OverviewCardUI(
        data = OverviewCardUIData(
            overviewTabSelectionIndex = overviewTabSelectionIndex,
            title = overviewCardData?.title.orEmpty(),
            pieChartData = pieChartData,
        ),
        events = OverviewCardUIEvents(
            onClick = onClick,
            onOverviewTabClick = {
                viewModel.setOverviewTabSelectionIndex(
                    updatedOverviewTabSelectionIndex = it,
                )
            },
            handleOverviewCardAction = {
                viewModel.handleOverviewCardAction(
                    overviewCardAction = it
                )
            },
        ),
    )
}
