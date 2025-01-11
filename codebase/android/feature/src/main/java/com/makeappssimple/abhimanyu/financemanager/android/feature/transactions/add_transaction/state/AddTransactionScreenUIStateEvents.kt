package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class AddTransactionScreenUIStateEvents(
    val clearAmount: () -> Unit = {},
    val clearTitle: () -> Unit = {},
    val insertTransaction: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val setAccountFrom: (updatedAccountFrom: Account?) -> Unit = {},
    val setAccountTo: (updatedAccountTo: Account?) -> Unit = {},
    val setAmount: (updatedAmount: TextFieldValue) -> Unit = {},
    val setCategory: (updatedCategory: Category?) -> Unit = {},
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val setScreenBottomSheetType: (AddTransactionScreenBottomSheetType) -> Unit = {},
    val setSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val setTransactionDate: (updatedTransactionDate: LocalDate) -> Unit = {},
    val setTransactionTime: (updatedTransactionTime: LocalTime) -> Unit = {},
) : ScreenUIStateEvents
