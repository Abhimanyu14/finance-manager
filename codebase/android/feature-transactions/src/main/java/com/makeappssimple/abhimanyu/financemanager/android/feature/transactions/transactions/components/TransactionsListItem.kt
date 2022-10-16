package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CurrencyExchange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemViewWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.adjustmentEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transferEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

@Composable
internal fun TransactionsListItem(
    modifier: Modifier = Modifier,
    transactionData: TransactionData,
    expanded: Boolean,
    deleteEnabled: Boolean,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRefundClick: () -> Unit,
) {
    val sourceFrom = transactionData.sourceFrom
    val sourceTo = transactionData.sourceTo

    MyExpandableItemViewWrapper(
        expanded = expanded,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (expanded) {
                        ExpandedListItemShape
                    } else {
                        MaterialTheme.shapes.large
                    },
                )
                .clickable {
                    onClick()
                }
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (expanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                    bottom = if (expanded) {
                        16.dp
                    } else {
                        8.dp
                    },
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
                            transactionData.transaction.transactionType == TransactionType.ADJUSTMENT
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
                    text = transactionData.transactionFor.titleToDisplay,
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
                                id = R.string.list_item_transactions_source,
                                sourceFrom.name,
                                sourceTo.name,
                            )
                        } else {
                            transactionData.sourceFrom?.name
                                ?: transactionData.sourceTo?.name.orEmpty()
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
        if (expanded) {
            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 0.5.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    ),
            ) {
                MyExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Edit,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    labelText = stringResource(
                        id = R.string.list_item_transactions_edit,
                    ),
                    enabled = true,
                    onClick = onEditClick,
                )
                // (transactionData.transaction.transactionType == TransactionType.EXPENSE)
                if (false) {
                    MyExpandableItemIconButton(
                        iconImageVector = Icons.Rounded.CurrencyExchange,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        labelText = stringResource(
                            id = R.string.list_item_transactions_refund,
                        ),
                        enabled = true,
                        onClick = onRefundClick,
                    )
                }
                MyExpandableItemIconButton(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                    iconImageVector = Icons.Rounded.Delete,
                    labelText = stringResource(
                        id = R.string.list_item_transactions_delete,
                    ),
                    enabled = deleteEnabled,
                    onClick = onDeleteClick,
                )
            }
        }
    }
}
