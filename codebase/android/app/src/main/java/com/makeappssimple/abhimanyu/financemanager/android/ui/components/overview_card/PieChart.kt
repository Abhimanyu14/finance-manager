package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChart
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SimpleSliceDrawer
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green700
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Red

const val sliceThickness = 30F
val pieChartData = PieChartData(
    slices = listOf(
        PieChartData.Slice(
            value = 10F,
            color = Green700,
        ),
        PieChartData.Slice(
            value = 10F,
            color = Red,
        ),
    )
)

@Composable
fun PieChart() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(
                vertical = 8.dp,
            )
    ) {
        PieChart(
            pieChartData = pieChartData,
            sliceDrawer = SimpleSliceDrawer(
                sliceThickness = sliceThickness
            ),
        )
    }
}
