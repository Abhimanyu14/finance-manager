package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.legend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartLegendItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.HorizontalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NonFillingVerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray

@Composable
fun PieChartLegend(
    modifier: Modifier = Modifier,
    items: List<PieChartLegendItemData>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        items.fastForEachIndexed { index, item ->
            key("${index}_${item.hashCode()}") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Dot(
                        color = item.color,
                    )
                    HorizontalSpacer(
                        width = 8.dp,
                    )
                    MyText(
                        text = item.text,
                        style = TextStyle(
                            color = DarkGray,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }

                if (index != items.lastIndex) {
                    NonFillingVerticalSpacer(
                        height = 4.dp,
                    )
                }
            }
        }
    }
}
