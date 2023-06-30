package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
data class OverviewCardUIData(
    val isLoading: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val pieChartData: PieChartData? = null,
    val title: String = "",
)

@Immutable
data class OverviewCardUIEvents(
    val onClick: (() -> Unit)? = null,
    val onOverviewTabClick: (index: Int) -> Unit = {},
    val handleOverviewCardAction: (overviewCardAction: OverviewCardAction) -> Unit = {},
)

@Composable
fun OverviewCardUI(
    modifier: Modifier = Modifier,
    data: OverviewCardUIData,
    events: OverviewCardUIEvents = OverviewCardUIEvents(),
) {
    if (data.isLoading) {
        OverviewCardLoadingUI(
            modifier = modifier,
        )
    } else {
        ElevatedCard(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp,
                )
                .clip(
                    shape = MaterialTheme.shapes.medium,
                )
                .conditionalClickable(
                    onClick = events.onClick,
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
                    selectedItemIndex = data.overviewTabSelectionIndex,
                    onClick = events.onOverviewTabClick,
                )
                VerticalSpacer(
                    height = 8.dp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // TODO-Abhi: Disable the buttons conditionally
                    IconButton(
                        onClick = {
                            events.handleOverviewCardAction(OverviewCardAction.DECREASE)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = null, // TODO-Abhi: Change content description
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    MyText(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 8.dp,
                            )
                            .weight(
                                weight = 1F,
                            ),
                        text = data.title,
                        style = MaterialTheme.typography.labelLarge
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                            ),
                    )
                    IconButton(
                        onClick = {
                            events.handleOverviewCardAction(OverviewCardAction.INCREASE)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = null, // TODO-Abhi: Change content description
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
                VerticalSpacer(
                    height = 8.dp,
                )
                data.pieChartData?.let { pieChartDataValue ->
                    ComposePieChart(
                        data = pieChartDataValue,
                    )
                }
            }
        }
    }
}

@Composable
private fun OverviewCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(
                size = DpSize(
                    width = 315.dp,
                    height = 223.dp,
                ),
            )
            .clip(
                shape = MaterialTheme.shapes.medium,
            )
            .shimmer(),
    )
}
