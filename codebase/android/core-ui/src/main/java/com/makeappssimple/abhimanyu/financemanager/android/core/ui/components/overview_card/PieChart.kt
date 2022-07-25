package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val sliceThickness = 30F
/*
val pieChartData = PieChartData(
    items = listOf(
        PieChartItemData(
            value = 10F,
            color = Green700,
        ),
        PieChartItemData(
            value = 10F,
            color = Red,
        ),
    )
)
*/

@Composable
fun PieChart(
    pieChartData: com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChartData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(
                vertical = 8.dp,
            )
    ) {
        com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChart(
            pieChartData = pieChartData,
            sliceDrawer = com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SimpleSliceDrawer(
                sliceThickness = sliceThickness
            ),
        )
    }
}
