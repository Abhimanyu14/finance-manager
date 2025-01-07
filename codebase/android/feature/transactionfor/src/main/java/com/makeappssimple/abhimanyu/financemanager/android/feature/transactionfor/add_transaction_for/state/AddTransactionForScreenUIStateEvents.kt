package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents

@Stable
internal class AddTransactionForScreenUIStateEvents(
    val clearTitle: () -> Unit = {},
    val insertTransactionFor: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
