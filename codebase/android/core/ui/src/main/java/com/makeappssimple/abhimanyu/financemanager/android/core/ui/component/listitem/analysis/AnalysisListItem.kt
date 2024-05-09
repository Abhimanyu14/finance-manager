package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircleData

@Immutable
public data class AnalysisListItemData(
    val maxEndTextWidth: Int = 0,
    val amountText: String,
    val emoji: String,
    val percentage: Float,
    val percentageText: String,
    val title: String,
)

@Composable
public fun AnalysisListItem(
    modifier: Modifier = Modifier,
    data: AnalysisListItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp,
            ),
    ) {
        MyEmojiCircle(
            data = MyEmojiCircleData(
                backgroundColor = MaterialTheme.colorScheme.outline,
                emoji = data.emoji,
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val density = LocalDensity.current
            val endTextWidth = with(
                receiver = density,
            ) {
                data.maxEndTextWidth.toDp() + 24.dp
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                        .weight(
                            weight = 1F,
                        ),
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineMedium
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                    LinearProgressIndicator(
                        progress = {
                            data.percentage
                        },
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = StrokeCap.Round,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                height = 8.dp,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                        )
                        .width(
                            width = endTextWidth,
                        ),
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.amountText,
                        style = MaterialTheme.typography.headlineMedium
                            .copy(
                                textAlign = TextAlign.End,
                            ),
                    )
                    Spacer(
                        modifier = Modifier
                            .height(
                                height = 4.dp,
                            ),
                    )
                    MyText(
                        modifier = Modifier,
                        text = data.percentageText,
                        style = MaterialTheme.typography.bodySmall
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
            }
        }
    }
}
