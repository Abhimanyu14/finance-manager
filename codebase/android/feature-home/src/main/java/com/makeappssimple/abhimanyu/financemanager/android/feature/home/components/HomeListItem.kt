package com.makeappssimple.abhimanyu.financemanager.android.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getReadableDateAndTimeString
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.adjustmentEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transferEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.R

@Composable
internal fun HomeListItem(
    transactionData: TransactionData,
) {
    val sourceFrom = transactionData.sourceFrom
    val sourceTo = transactionData.sourceTo

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
    ) {
        val emoji = when (transactionData.transaction.transactionType) {
            TransactionType.TRANSFER -> {
                transferEmoji
            }

            TransactionType.ADJUSTMENT -> {
                adjustmentEmoji
            }

            else -> {
                transactionData.category?.emoji
            }
        }
        MyEmojiCircle(
            backgroundColor = MaterialTheme.colorScheme.outline,
            emoji = emoji,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = transactionData.transaction.title,
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = if (transactionData.transaction.transactionType == TransactionType.INCOME ||
                        transactionData.transaction.transactionType == TransactionType.EXPENSE ||
                        transactionData.transaction.transactionType == TransactionType.ADJUSTMENT ||
                        transactionData.transaction.transactionType == TransactionType.REFUND
                    ) {
                        transactionData.transaction.amount.toSignedString(
                            isPositive = transactionData.sourceTo != null,
                            isNegative = transactionData.sourceFrom != null,
                        )
                    } else {
                        transactionData.transaction.amount.toString()
                    },
                    style = MaterialTheme.typography.headlineMedium
                        .copy(
                            color = transactionData.transaction.getAmountTextColor(),
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
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = transactionData.transactionFor.titleToDisplay.orEmpty(),
                style = MaterialTheme.typography.bodySmall
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = getReadableDateAndTimeString(
                        timestamp = transactionData.transaction.transactionTimestamp,
                    ),
                    style = MaterialTheme.typography.bodySmall
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                )
                MyText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(
                            weight = 1F,
                        ),
                    text = if (sourceFrom != null && sourceTo != null) {
                        stringResource(
                            id = R.string.list_item_home_source,
                            sourceFrom.name,
                            sourceTo.name,
                        )
                    } else {
                        sourceFrom?.name ?: sourceTo?.name.orEmpty()
                    },
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
