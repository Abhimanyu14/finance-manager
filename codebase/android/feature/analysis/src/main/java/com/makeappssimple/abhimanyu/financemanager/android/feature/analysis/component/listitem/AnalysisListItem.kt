package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyItemViewWrapper

@Immutable
data class AnalysisListItemData(
    val amountText: String,
    val emoji: String,
    val percentage: String,
    val title: String,
)

@Immutable
internal data class AnalysisListItemEvents(
    val onClick: (() -> Unit)? = null,
)

@Composable
internal fun AnalysisListItem(
    modifier: Modifier = Modifier,
    data: AnalysisListItemData,
    events: AnalysisListItemEvents,
) {
    MyItemViewWrapper(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = MaterialTheme.shapes.large,
                )
                .conditionalClickable(
                    onClick = events.onClick,
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp,
                ),
        ) {
            MyEmojiCircle(
                backgroundColor = MaterialTheme.colorScheme.outline,
                emoji = data.emoji,
            )
            MyText(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                    )
                    .weight(
                        weight = 1F,
                    ),
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier,
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.amountText,
                        style = MaterialTheme.typography.headlineMedium
                            .copy(
                                textAlign = TextAlign.End,
                            ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(
                            height = 4.dp,
                        ),
                )
                Row(
                    modifier = Modifier
                ) {
                    MyText(
                        modifier = Modifier,
                        text = data.percentage,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
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
