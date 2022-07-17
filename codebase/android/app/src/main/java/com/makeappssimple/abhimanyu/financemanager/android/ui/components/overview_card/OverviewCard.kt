package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Blue50
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.VerticalSpacer

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
    val overviewTabSelectionIndex by viewModel.overviewTabSelectionIndex.collectAsState()
    val pieChartData by viewModel.pieChartData.collectAsState()

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp,
            )
            .clip(
                shape = RoundedCornerShape(
                    size = 16.dp,
                ),
            )
            .conditionalClickable(
                onClick = onClick,
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Blue50,
                )
                .padding(
                    all = 12.dp,
                )
                .animateContentSize(),
        ) {
            OverviewTab(
                data = OverviewTabData(
                    items = OverviewTabOption.values()
                        .map {
                            it.title
                        },
                    selectedItemIndex = overviewTabSelectionIndex,
                    onClick = {
                        viewModel.setOverviewTabSelectionIndex(
                            updatedOverviewTabSelectionIndex = it,
                        )
                    },
                ),
            )
            VerticalSpacer(
                height = 8.dp,
            )
            pieChartData?.let { pieChartDataValue ->
                ComposePieChart(
                    data = pieChartDataValue,
                )
            }
        }
    }
}
