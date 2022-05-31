package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.conditionalClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewCard(
    viewModel: OverviewCardViewModel = hiltViewModel<OverviewCardViewModelImpl>(),
    onClick: (() -> Unit)? = null,
) {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 12.dp,
                )
                .animateContentSize(),
        ) {
            pieChartData?.let { pieChartDataValue ->
                ComposePieChart(
                    data = pieChartDataValue,
                )
            }
        }
    }
}
