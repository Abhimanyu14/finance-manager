package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType

@Stable
internal class EditTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit = {},
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit = {},
    val setScreenBottomSheetType: (EditTransactionScreenBottomSheetType) -> Unit = {},
) : ScreenUIStateEvents
