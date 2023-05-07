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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.getComposeColor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.MyExpandableItemViewWrapper

data class TransactionListItemData(
    val modifier: Modifier = Modifier,
    val isDeleteButtonEnabled: Boolean = false,
    val isDeleteButtonVisible: Boolean = false,
    val isEditButtonVisible: Boolean = false,
    val isExpanded: Boolean = false,
    val isRefundButtonVisible: Boolean = false,
    val transactionId: Int,
    val amountColor: MyColor,
    val amountText: String,
    val dateAndTimeText: String,
    val emoji: String,
    val sourceFromName: String?,
    val sourceToName: String?,
    val title: String,
    val transactionForText: String,
    val onClick: (() -> Unit)? = null,
    val onDeleteButtonClick: () -> Unit = {},
    val onEditButtonClick: () -> Unit = {},
    val onRefundButtonClick: () -> Unit = {},
)

@Composable
fun TransactionListItem(
    data: TransactionListItemData,
) {
    val sourceText: String = if (
        data.sourceFromName.isNotNull() &&
        data.sourceToName.isNotNull()
    ) {
        stringResource(
            id = R.string.transaction_list_item_source,
            data.sourceFromName,
            data.sourceToName,
        )
    } else {
        data.sourceFromName ?: data.sourceToName.orEmpty()
    }

    MyExpandableItemViewWrapper(
        expanded = data.isExpanded,
        modifier = data.modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (data.isExpanded) {
                        ExpandedListItemShape
                    } else {
                        MaterialTheme.shapes.large
                    },
                )
                .conditionalClickable(
                    onClick = data.onClick,
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (data.isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                    bottom = if (data.isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                ),
        ) {
            MyEmojiCircle(
                backgroundColor = MaterialTheme.colorScheme.outline,
                emoji = data.emoji,
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
                        text = data.title,
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
                        text = data.amountText,
                        style = MaterialTheme.typography.headlineMedium
                            .copy(
                                color = data.amountColor.getComposeColor(),
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
                    text = data.transactionForText,
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
                        text = data.dateAndTimeText,
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
            visible = data.isExpanded,
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
                if (data.isEditButtonVisible) {
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
                        onClick = data.onEditButtonClick,
                    )
                }
                if (data.isRefundButtonVisible) {
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
                        onClick = data.onRefundButtonClick,
                    )
                }
                if (data.isDeleteButtonVisible) {
                    MyExpandableItemIconButton(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        iconImageVector = Icons.Rounded.Delete,
                        labelText = stringResource(
                            id = R.string.transaction_list_item_delete,
                        ),
                        enabled = data.isDeleteButtonEnabled,
                        onClick = data.onDeleteButtonClick,
                    )
                }
            }
        }
    }
}
