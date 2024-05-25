package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import java.time.LocalDate
import java.time.LocalTime

@Immutable
public data class EditTransactionScreenUiStateData(
    val selectedTransactionTypeIndex: Int? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val accountFrom: Account? = null,
    val accountTo: Account? = null,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
)

public fun EditTransactionScreenUiStateData?.orDefault(): EditTransactionScreenUiStateData {
    return if (this.isNull()) {
        EditTransactionScreenUiStateData()
    } else {
        this
    }
}
