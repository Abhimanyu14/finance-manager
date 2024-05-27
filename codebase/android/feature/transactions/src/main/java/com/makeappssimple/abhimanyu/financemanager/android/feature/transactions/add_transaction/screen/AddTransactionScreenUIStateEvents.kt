package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class AddTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit,
    val setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit,
    val setScreenBottomSheetType: (AddTransactionScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents
