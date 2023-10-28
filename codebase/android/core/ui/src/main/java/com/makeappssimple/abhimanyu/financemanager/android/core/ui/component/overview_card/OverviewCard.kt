package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.constants.TestTags.COMPONENT_OVERVIEW_CARD
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
data class OverviewCardData(
    val isLoading: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val title: String = "",
    val pieChartData: PieChartData? = null,
)

@Immutable
data class OverviewCardEvents(
    val onClick: (() -> Unit)? = null,
    val onOverviewTabClick: (index: Int) -> Unit = {},
    val handleOverviewCardAction: (overviewCardAction: OverviewCardAction) -> Unit = {},
)

enum class OverviewTabOption(
    val title: String,
) {
    DAY("DAY"),

    // TODO(Abhi): Enable week later
    // WEEK("Week"),
    MONTH("MONTH"),
    YEAR("YEAR"),
}

enum class OverviewCardAction {
    NEXT,
    PREV,
}

data class OverviewCardViewModelData(
    val income: Float = 0F,
    val expense: Float = 0F,
    val title: String = "",
)

fun OverviewCardViewModelData?.orDefault(): OverviewCardViewModelData {
    return if (this.isNull()) {
        OverviewCardViewModelData()
    } else {
        this
    }
}

@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    data: OverviewCardData,
    events: OverviewCardEvents = OverviewCardEvents(),
) {
    if (data.isLoading) {
        OverviewCardLoadingUI(
            modifier = modifier
                .testTag(COMPONENT_OVERVIEW_CARD),
        )
    } else {
        ElevatedCard(
            modifier = modifier
                .testTag(COMPONENT_OVERVIEW_CARD)
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
                    data = OverviewTabData(
                        items = OverviewTabOption.values()
                            .map {
                                it.title
                            },
                        selectedItemIndex = data.overviewTabSelectionIndex,
                    ),
                    events = OverviewTabEvents(
                        onClick = events.onOverviewTabClick,
                    ),
                )
                VerticalSpacer(
                    height = 8.dp,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // TODO(Abhi): Disable the buttons conditionally
                    IconButton(
                        onClick = {
                            events.handleOverviewCardAction(OverviewCardAction.PREV)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = null, // TODO(Abhi): Change content description
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
                            events.handleOverviewCardAction(OverviewCardAction.NEXT)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = null, // TODO(Abhi): Change content description
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
            .fillMaxWidth()
            .height(
                height = 223.dp,
            )
            .padding(
                horizontal = 32.dp,
                vertical = 16.dp,
            )
            .clip(
                shape = MaterialTheme.shapes.medium,
            )
            .shimmer(),
    )
}
