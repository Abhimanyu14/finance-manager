package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.total_balance_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

data class TotalBalanceCardViewData(
    val totalBalanceAmount: Long,
    val onClick: (() -> Unit)? = null,
)

@Composable
fun TotalBalanceCardView(
    data: TotalBalanceCardViewData,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 16.dp,
                horizontal = 32.dp,
            )
            .clip(
                MaterialTheme.shapes.medium,
            )
            .conditionalClickable(
                onClick = data.onClick,
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
