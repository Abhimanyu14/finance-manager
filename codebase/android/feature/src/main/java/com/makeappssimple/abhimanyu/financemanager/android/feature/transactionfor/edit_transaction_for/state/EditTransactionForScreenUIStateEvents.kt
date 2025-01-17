package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType

@Stable
internal class EditTransactionForScreenUIStateEvents(
    val clearTitle: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateScreenBottomSheetType: (EditTransactionForScreenBottomSheetType) -> Unit = {},
    val updateTitle: (updatedTitle: TextFieldValue) -> Unit = {},
    val updateTransactionFor: () -> Unit = {},
) : ScreenUIStateEvents
