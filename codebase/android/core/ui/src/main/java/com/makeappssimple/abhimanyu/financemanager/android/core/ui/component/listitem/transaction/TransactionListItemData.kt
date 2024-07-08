package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor

@Immutable
public data class TransactionListItemData(
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

public fun TransactionData.toTransactionListItemData(
    dateTimeUtil: DateTimeUtil,
): TransactionListItemData {
    val amountText: String = when (transaction.transactionType) {
        TransactionType.INCOME,
        TransactionType.EXPENSE,
        TransactionType.ADJUSTMENT,
        TransactionType.REFUND,
        -> {
            transaction.amount.toSignedString(
                isPositive = accountTo.isNotNull(),
                isNegative = accountFrom.isNotNull(),
            )
        }

        else -> {
            transaction.amount.toString()
        }
    }
    val dateAndTimeText: String = dateTimeUtil.getReadableDateAndTime(
        timestamp = transaction.transactionTimestamp,
    )
    val emoji: String = when (transaction.transactionType) {
        TransactionType.TRANSFER -> {
            EmojiConstants.LEFT_RIGHT_ARROW
        }

        TransactionType.ADJUSTMENT -> {
            EmojiConstants.EXPRESSIONLESS_FACE
        }

        else -> {
            category?.emoji.orEmpty()
        }
    }
    return TransactionListItemData(
        transactionId = transaction.id,
        amountColor = transaction.getAmountTextColor(),
        amountText = amountText,
        dateAndTimeText = dateAndTimeText,
        emoji = emoji,
        accountFromName = accountFrom?.name,
        accountToName = accountTo?.name,
        title = transaction.title,
        transactionForText = transactionFor.titleToDisplay,
    )
}
