package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class AddTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val setScreenBottomSheetType: (AddTransactionScreenBottomSheetType) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
    val setAmount: (updatedAmount: TextFieldValue) -> Unit = {},
    val clearAmount: () -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val clearTitle: () -> Unit = {},
    val setCategory: (updatedCategory: Category?) -> Unit = {},
    val setSelectedTransactionForIndex: (updatedSelectedTransactionForIndex: Int) -> Unit = {},
    val setAccountFrom: (updatedAccountFrom: Account?) -> Unit = {},
    val setAccountTo: (updatedAccountTo: Account?) -> Unit = {},
    val setTransactionDate: (updatedTransactionDate: LocalDate) -> Unit = {},
    val setTransactionTime: (updatedTransactionTime: LocalTime) -> Unit = {},
) : ScreenUIStateEvents
