package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.chart.composepie.ComposePieChart
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags.COMPONENT_OVERVIEW_CARD
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
public sealed class OverviewCardEvent {
    public data object OnClick : OverviewCardEvent()

    public data class OnOverviewTabClick(
        val index: Int,
    ) : OverviewCardEvent()

    public data class OnOverviewCardAction(
        val overviewCardAction: OverviewCardAction,
    ) : OverviewCardEvent()
}

public enum class OverviewTabOption(
    public val title: String,
) {
    DAY(
        title = "DAY",
    ),

    // TODO(Abhi): Enable week later
    // WEEK("Week"),

    MONTH(
        title = "MONTH",
    ),
    YEAR(
        title = "YEAR",
    ),
}

public enum class OverviewCardAction {
    NEXT,
    PREV,
}

public data class OverviewCardViewModelData(
    val income: Float = 0F,
    val expense: Float = 0F,
    val title: String = "",
)

public fun OverviewCardViewModelData?.orDefault(): OverviewCardViewModelData {
    return if (this.isNull()) {
        OverviewCardViewModelData()
    } else {
        this
    }
}

@Composable
public fun OverviewCard(
    modifier: Modifier = Modifier,
    data: OverviewCardData,
    handleEvent: (event: OverviewCardEvent) -> Unit = {},
) {
    val modifierWithTestTag = modifier
        .testTag(
            tag = COMPONENT_OVERVIEW_CARD,
        )
    if (data.isLoading) {
        OverviewCardLoadingUI(
            modifier = modifierWithTestTag,
        )
    } else {
        OverviewCardUI(
            modifier = modifierWithTestTag,
            data = data,
            handleEvent = handleEvent,
        )
    }
}

@Composable
private fun OverviewCardUI(
    modifier: Modifier = Modifier,
    data: OverviewCardData,
    handleEvent: (event: OverviewCardEvent) -> Unit = {},
) {
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
                onClick = {
                    handleEvent(OverviewCardEvent.OnClick)
                },
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
                    items = OverviewTabOption.entries
                        .map {
                            it.title
                        },
                    selectedItemIndex = data.overviewTabSelectionIndex,
                ),
                handleEvent = { event ->
                    when (event) {
                        is OverviewTabEvent.OnClick -> {
                            handleEvent(
                                OverviewCardEvent.OnOverviewTabClick(
                                    index = event.index,
                                )
                            )
                        }
                    }
                },
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
                MyIconButton(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = MyIcons.ChevronLeft,
                    contentDescriptionStringResourceId = R.string.overview_card_previous_button_content_description,
                    onClick = {
                        handleEvent(
                            OverviewCardEvent.OnOverviewCardAction(
                                overviewCardAction = OverviewCardAction.PREV,
                            )
                        )
                    },
                )
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
                MyIconButton(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = MyIcons.ChevronRight,
                    contentDescriptionStringResourceId = R.string.overview_card_next_button_content_description,
                    onClick = {
                        handleEvent(
                            OverviewCardEvent.OnOverviewCardAction(
                                overviewCardAction = OverviewCardAction.NEXT,
                            )
                        )
                    },
                )
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
