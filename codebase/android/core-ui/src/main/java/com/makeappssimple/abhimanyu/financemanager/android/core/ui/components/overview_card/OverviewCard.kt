package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount

enum class OverviewTabOption(
    val title: String,
) {
    DAY("Day"),

    // TODO-Abhi: Enable week later
    // WEEK("Week"),
    MONTH("Month"),
    YEAR("Year"),
}

@Composable
fun OverviewCard(
    viewModel: OverviewCardViewModel = hiltViewModel<OverviewCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
    val overviewTabSelectionIndex by viewModel.overviewTabSelectionIndex.collectAsStateWithLifecycle()
    val amountData by viewModel.amountData.collectAsStateWithLifecycle()
    val totalIncomeAmount = Amount(
        value = amountData?.getOrNull(0)?.toLong() ?: 0L,
    )
    val totalExpenseAmount = Amount(
        value = amountData?.getOrNull(1)?.toLong() ?: 0L,
    )
    val pieChartData = PieChartData(
        items = listOf(
            PieChartItemData(
                value = amountData?.getOrNull(0) ?: 0F,
                text = "Income : $totalIncomeAmount",
                color = MaterialTheme.colorScheme.tertiary,
            ),
            PieChartItemData(
                value = amountData?.getOrNull(1) ?: 0F,
                text = "Expense : ${totalExpenseAmount.toNonSignedString()}",
                color = MaterialTheme.colorScheme.error,
            ),
        ),
    )

    OverviewCardView(
        data = OverviewCardViewData(
            onClick = onClick,
            overviewTabSelectionIndex = overviewTabSelectionIndex,
            onOverviewTabClick = {
                viewModel.setOverviewTabSelectionIndex(
                    updatedOverviewTabSelectionIndex = it,
                )
            },
            pieChartData = pieChartData,
        ),
    )
}
