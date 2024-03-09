package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.getComposeColor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemUIWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle.MyEmojiCircleData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.icons.MyIcons

@Immutable
data class TransactionListItemData(
    val isDeleteButtonEnabled: Boolean = false,
    val isDeleteButtonVisible: Boolean = false,
    val isEditButtonVisible: Boolean = false,
    val isExpanded: Boolean = false,
    val isInSelectionMode: Boolean = false,
    val isRefundButtonVisible: Boolean = false,
    val isSelected: Boolean = false,
    val transactionId: Int,
    val amountColor: MyColor,
    val amountText: String,
    val dateAndTimeText: String,
    val emoji: String,
    val accountFromName: String?,
    val accountToName: String?,
    val title: String,
    val transactionForText: String,
)

@Immutable
data class TransactionListItemEvents(
    val onClick: (() -> Unit)? = null,
    val onDeleteButtonClick: () -> Unit = {},
    val onEditButtonClick: () -> Unit = {},
    val onLongClick: (Boolean) -> Unit = {},
    val onRefundButtonClick: () -> Unit = {},
)

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    data: TransactionListItemData,
    events: TransactionListItemEvents = TransactionListItemEvents(),
) {
    val accountText: String = if (
        data.accountFromName.isNotNull() &&
        data.accountToName.isNotNull()
    ) {
        stringResource(
            id = R.string.transaction_list_item_account,
            data.accountFromName,
            data.accountToName,
        )
    } else {
        data.accountFromName ?: data.accountToName.orEmpty()
    }

    MyExpandableItemUIWrapper(
        isExpanded = data.isExpanded,
        isSelected = data.isSelected,
        modifier = modifier
            .padding(
                vertical = 4.dp,
            ),
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
                    onClick = events.onClick,
                    onLongClick = {
                        events.onLongClick(true)
                    },
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (data.isExpanded) {
                        16.dp
                    } else {
                        4.dp
                    },
                    bottom = if (data.isExpanded) {
                        16.dp
                    } else {
                        4.dp
                    },
                ),
        ) {
            if (data.isInSelectionMode) {
                if (data.isSelected) {
                    Icon(
                        imageVector = MyIcons.CheckCircle,
                        // TODO(Abhi) - Add content description
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                } else {
                    Icon(
                        imageVector = MyIcons.RadioButtonUnchecked,
                        // TODO(Abhi) - Add content description
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                }
            } else {
                MyEmojiCircle(
                    data = MyEmojiCircleData(
                        backgroundColor = MaterialTheme.colorScheme.outline,
                        emoji = data.emoji,
                    ),
                )
            }
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
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
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
                            height = 0.dp,
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
                            height = 0.dp,
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
                        text = accountText,
                        style = MaterialTheme.typography.bodySmall
                            .copy(
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.End,
                            ),
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(
                            height = 2.dp,
                        ),
                )
                AnimatedVisibility(
                    visible = data.isExpanded.not() && data.isInSelectionMode.not(),
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline,
                        thickness = 0.5.dp,
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = data.isExpanded,
        ) {
            HorizontalDivider(
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
                        iconImageVector = MyIcons.Edit,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        labelText = stringResource(
                            id = R.string.transaction_list_item_edit,
                        ),
                        enabled = true,
                        onClick = events.onEditButtonClick,
                    )
                }
                if (data.isRefundButtonVisible) {
                    MyExpandableItemIconButton(
                        iconImageVector = MyIcons.CurrencyExchange,
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        labelText = stringResource(
                            id = R.string.transaction_list_item_refund,
                        ),
                        enabled = true,
                        onClick = events.onRefundButtonClick,
                    )
                }
                if (data.isDeleteButtonVisible) {
                    MyExpandableItemIconButton(
                        modifier = Modifier
                            .weight(
                                weight = 1F,
                            ),
                        iconImageVector = MyIcons.Delete,
                        labelText = stringResource(
                            id = R.string.transaction_list_item_delete,
                        ),
                        enabled = data.isDeleteButtonEnabled,
                        onClick = events.onDeleteButtonClick,
                    )
                }
            }
        }
    }
}
