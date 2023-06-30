package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
data class TotalBalanceCardData(
    val isLoading: Boolean = false,
    val totalBalanceAmount: Long = 0L,
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
            modifier = modifier,
        )
    } else {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 32.dp,
                    vertical = 16.dp,
                )
                .clip(
                    MaterialTheme.shapes.medium,
                )
                .conditionalClickable(
                    onClick = events.onClick,
                ),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
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
            .size(
                size = DpSize(
                    width = 315.dp,
                    height = 96.dp,
                ),
            )
            .clip(
                shape = MaterialTheme.shapes.medium,
            )
            .shimmer(),
    )
}
