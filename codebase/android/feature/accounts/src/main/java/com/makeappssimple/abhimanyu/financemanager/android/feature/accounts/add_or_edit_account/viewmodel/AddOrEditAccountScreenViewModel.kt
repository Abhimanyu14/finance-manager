package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AddOrEditAccountScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?>

    fun insertAccount()

    fun clearName()

    fun navigateUp()

    fun updateName(
        updatedName: TextFieldValue,
    )

    fun clearBalanceAmountValue()

    fun clearMinimumAccountBalanceAmountValue()

    fun updateBalanceAmountValue(
        updatedBalanceAmountValue: TextFieldValue,
    )

    fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    )

    fun updateSelectedAccountTypeIndex(
        updatedIndex: Int,
    )

    fun updateAccount()
}
