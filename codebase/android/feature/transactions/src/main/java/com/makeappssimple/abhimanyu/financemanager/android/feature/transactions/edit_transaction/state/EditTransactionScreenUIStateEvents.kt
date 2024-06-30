package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class EditTransactionScreenUIStateEvents(
    val clearAmount: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setAccountFrom: (updatedAccountFrom: Account?) -> Unit = {},
    val setAccountTo: (updatedAccountTo: Account?) -> Unit = {},
    val setAmount: (updatedAmount: TextFieldValue) -> Unit = {},
    val setCategory: (updatedCategory: Category?) -> Unit = {},
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val setScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Unit = {},
    val setSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val setTransactionDate: (updatedTransactionDate: LocalDate) -> Unit = {},
    val setTransactionTime: (updatedTransactionTime: LocalTime) -> Unit = {},
    val updateTransaction: () -> Unit = {},
) : ScreenUIStateEvents
