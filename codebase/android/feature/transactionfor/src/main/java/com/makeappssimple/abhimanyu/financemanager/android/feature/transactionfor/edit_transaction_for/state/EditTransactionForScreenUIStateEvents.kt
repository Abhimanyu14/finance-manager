package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class EditTransactionForScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
