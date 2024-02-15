package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.TestTags
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.matchrowsize.matchRowSize
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons

@Immutable
data class TotalBalanceCardData(
    val isLoading: Boolean = false,
    val totalBalanceAmount: Long = 0L,
    val totalMinimumBalanceAmount: Long = 0L,
)

@Immutable
data class TotalBalanceCardEvents(
    val onClick: (() -> Unit)? = null,
    val onLongClick: (() -> Unit)? = null,
)

@Composable
fun TotalBalanceCard(
    modifier: Modifier = Modifier,
    data: TotalBalanceCardData,
    events: TotalBalanceCardEvents = TotalBalanceCardEvents(),
) {
    if (data.isLoading) {
        TotalBalanceCardLoadingUI(
            modifier = modifier
                .testTag(TestTags.COMPONENT_TOTAL_BALANCE_CARD),
        )
    } else {
        Column(
            modifier = modifier
                .testTag(TestTags.COMPONENT_TOTAL_BALANCE_CARD)
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp,
                )
                .clip(
                    MaterialTheme.shapes.medium,
                )
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                )
                .conditionalClickable(
                    onClick = events.onClick,
                )
                .padding(
                    all = 16.dp,
                ),
        ) {
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = R.string.total_balance_card_title,
                style = MaterialTheme.typography.displaySmall
                    .copy(
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = Amount(
                    value = data.totalBalanceAmount,
                ).toString(),
                style = MaterialTheme.typography.displayLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onTertiary,
                        textAlign = TextAlign.Center,
                    ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(2.dp)
                        .matchRowSize(),
                    imageVector = MyIcons.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary,
                )
                MyText(
                    text = Amount(
                        value = data.totalMinimumBalanceAmount,
                    ).toString(),
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiary,
                            textAlign = TextAlign.End,
                        ),
                )
            }
        }
    }
}

@Composable
private fun TotalBalanceCardLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(
                height = 96.dp,
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
