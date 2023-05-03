package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemViewWrapper

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    isDeleteButtonEnabled: Boolean = false,
    isDeleteButtonVisible: Boolean = false,
    isEditButtonVisible: Boolean = false,
    isExpanded: Boolean = false,
    isRefundButtonVisible: Boolean = false,
    amountColor: Color,
    amountText: String,
    dateAndTimeText: String,
    emoji: String,
    sourceFromName: String?,
    sourceToName: String?,
    title: String,
    transactionForText: String,
    onClick: (() -> Unit)? = null,
    onDeleteButtonClick: () -> Unit = {},
    onEditButtonClick: () -> Unit = {},
    onRefundButtonClick: () -> Unit = {},
) {
    val sourceText: String = if (sourceFromName.isNotNull() && sourceToName.isNotNull()) {
        stringResource(
            id = R.string.transaction_list_item_source,
            sourceFromName,
            sourceToName,
        )
    } else {
        sourceFromName ?: sourceToName.orEmpty()
    }

    MyExpandableItemViewWrapper(
        expanded = isExpanded,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (isExpanded) {
                        ExpandedListItemShape
                    } else {
                        MaterialTheme.shapes.large
                    },
                )
                .conditionalClickable(
                    onClick = onClick,
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                    bottom = if (isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                ),
        ) {
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
                        text = title,
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
                        text = amountText,
                        style = MaterialTheme.typography.headlineMedium
                            .copy(
                                color = amountColor,
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
                    text = transactionForText,
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
                        text = dateAndTimeText,
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
                        text = sourceText,
                        style = MaterialTheme.typography.bodySmall
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
        ) {
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
                if (isEditButtonVisible) {
                    MyExpandableItemIconButton(
                        iconImageVector = Icons.Rounded.Edit,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        labelText = stringResource(
                            id = R.string.transaction_list_item_edit,
                        ),
                        enabled = true,
                        onClick = onEditButtonClick,
                    )
                }
                if (isRefundButtonVisible) {
                    MyExpandableItemIconButton(
                        iconImageVector = Icons.Rounded.CurrencyExchange,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        labelText = stringResource(
                            id = R.string.transaction_list_item_refund,
                        ),
                        enabled = true,
                        onClick = onRefundButtonClick,
                    )
                }
                if (isDeleteButtonVisible) {
                    MyExpandableItemIconButton(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        iconImageVector = Icons.Rounded.Delete,
                        labelText = stringResource(
                            id = R.string.transaction_list_item_delete,
                        ),
                        enabled = isDeleteButtonEnabled,
                        onClick = onDeleteButtonClick,
                    )
                }
            }
        }
    }
}
