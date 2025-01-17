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
    val resetScreenSnackbarType: () -> Unit = {},
    val updateAccountFrom: (updatedAccountFrom: Account?) -> Unit = {},
    val updateAccountTo: (updatedAccountTo: Account?) -> Unit = {},
    val updateAmount: (updatedAmount: TextFieldValue) -> Unit = {},
    val updateCategory: (updatedCategory: Category?) -> Unit = {},
    val updateIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val updateIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val updateScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Unit = {},
    val updateSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val updateTransaction: () -> Unit = {},
    val updateTransactionDate: (updatedTransactionDate: LocalDate) -> Unit = {},
    val updateTransactionTime: (updatedTransactionTime: LocalTime) -> Unit = {},
) : ScreenUIStateEvents
