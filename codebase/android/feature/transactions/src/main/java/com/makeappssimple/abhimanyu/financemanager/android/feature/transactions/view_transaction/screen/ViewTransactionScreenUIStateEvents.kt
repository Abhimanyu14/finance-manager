package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class ViewTransactionScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (ViewTransactionScreenBottomSheetType) -> Unit = {},
    val setTransactionIdToDelete: (Int?) -> Unit = {},
) : ScreenUIStateEvents
