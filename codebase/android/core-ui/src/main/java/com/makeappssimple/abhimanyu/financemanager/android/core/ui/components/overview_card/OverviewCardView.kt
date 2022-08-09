package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

@Composable
fun OverviewCardView(
    modifier: Modifier = Modifier,
    pieChartData: PieChartData?,
    overviewTabSelectionIndex: Int,
    onClick: (() -> Unit)? = null,
    onOverviewTabClick: (index: Int) -> Unit,
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp,
            )
            .clip(
                shape = MaterialTheme.shapes.medium,
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
                    color = MaterialTheme.colorScheme.primaryContainer,
                )
                .padding(
                    all = 12.dp,
                )
                .animateContentSize(),
        ) {
            OverviewTab(
                items = OverviewTabOption.values()
                    .map {
                        it.title
                    },
                selectedItemIndex = overviewTabSelectionIndex,
                onClick = {
                    onOverviewTabClick(it)
                },
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
