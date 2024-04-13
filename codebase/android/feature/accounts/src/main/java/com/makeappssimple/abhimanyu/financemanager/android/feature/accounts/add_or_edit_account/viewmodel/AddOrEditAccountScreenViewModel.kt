package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AddOrEditAccountScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?>

    public fun initViewModel()

    public fun handleUIEvents(
        uiEvent: AddOrEditAccountScreenUIEvent,
    )

    public fun insertAccount()

    public fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    )

    public fun updateAccount()
}
