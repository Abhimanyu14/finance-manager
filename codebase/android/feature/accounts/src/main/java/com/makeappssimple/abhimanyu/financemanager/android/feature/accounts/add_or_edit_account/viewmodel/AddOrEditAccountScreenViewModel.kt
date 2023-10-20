package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditAccountScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?>

    fun handleUIEvents(
        uiEvent: AddOrEditAccountScreenUIEvent,
    )

    fun insertAccount()

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    )

    fun updateAccount()
}
