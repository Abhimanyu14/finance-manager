package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class AddTransactionForScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setTitle: (updatedTitle: TextFieldValue) -> Unit = {},
) : ScreenUIStateEvents
