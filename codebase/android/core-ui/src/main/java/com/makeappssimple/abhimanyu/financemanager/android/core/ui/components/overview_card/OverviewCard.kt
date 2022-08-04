package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
    val pieChartData by viewModel.pieChartData.collectAsStateWithLifecycle()

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
