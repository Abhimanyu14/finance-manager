package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.InsertAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIError
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen.AddOrEditAccountScreenUIErrorData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddAccountScreenViewModel @Inject constructor(
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val insertAccountsUseCase: InsertAccountsUseCase,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val accounts: MutableList<Account> = mutableListOf()
    private val validAccountTypes: List<AccountType> = AccountType.entries.filter {
        it != AccountType.CASH
    }

    // region UI data
    private val selectedAccountTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = validAccountTypes.indexOf(
            element = AccountType.BANK,
        ),
    )
    private val name: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
    )
    private val minimumAccountBalanceAmountValue: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    private val errorData: MutableStateFlow<AddOrEditAccountScreenUIErrorData> = MutableStateFlow(
        value = AddOrEditAccountScreenUIErrorData(),
    )

    public val screenUIData: StateFlow<MyResult<AddOrEditAccountScreenUIData>?> = combine(
        errorData,
        selectedAccountTypeIndex,
        name,
        minimumAccountBalanceAmountValue,
    ) {
            errorData,
            selectedAccountTypeIndex,
            name,
            minimumAccountBalanceAmountValue,
        ->

        val isValidAccountData = checkIfAccountDataIsValid(
            name = name.text,
        )

        if (
            selectedAccountTypeIndex.isNull() ||
            name.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddOrEditAccountScreenUIData(
                    errorData = errorData,
                    isValidAccountData = isValidAccountData,
                    accountIsNotCash = true,
                    selectedAccountTypeIndex = selectedAccountTypeIndex,
                    accountTypes = validAccountTypes,
                    balanceAmountValue = TextFieldValue(),
                    minimumBalanceAmountValue = minimumAccountBalanceAmountValue,
                    name = name,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    // endregion

    public fun initViewModel() {
        getAllAccounts()
    }

    public fun insertAccount() {
        viewModelScope.launch {
            val accountType = validAccountTypes[selectedAccountTypeIndex.value]
            val minimumAccountBalanceAmount = if (accountType == AccountType.BANK) {
                Amount(
                    value = minimumAccountBalanceAmountValue.value.text.toLongOrZero(),
                )
            } else {
                null
            }
            insertAccountsUseCase(
                Account(
                    balanceAmount = Amount(
                        value = 0L,
                    ),
                    type = accountType,
                    minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                    name = name.value.text,
                ),
            )
            // TODO(Abhi): Use result of InsertAccountsUseCase and navigate back or show error
            navigator.navigateUp()
        }
    }

    public fun clearName() {
        updateName(
            updatedName = name.value.copy(
                text = "",
            ),
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateName(
        updatedName: TextFieldValue,
    ) {
        name.update {
            updatedName
        }
    }

    public fun clearMinimumAccountBalanceAmountValue() {
        updateMinimumAccountBalanceAmountValue(
            updatedMinimumAccountBalanceAmountValue = minimumAccountBalanceAmountValue.value.copy(
                text = "",
            ),
        )
    }

    public fun updateMinimumAccountBalanceAmountValue(
        updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) {
        minimumAccountBalanceAmountValue.update {
            updatedMinimumAccountBalanceAmountValue.copy(
                text = updatedMinimumAccountBalanceAmountValue.text.filterDigits(),
            )
        }
    }

    public fun updateSelectedAccountTypeIndex(
        updatedIndex: Int,
    ) {
        selectedAccountTypeIndex.update {
            updatedIndex
        }
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            accounts.addAll(getAllAccountsUseCase())
        }
    }

    private fun checkIfAccountDataIsValid(
        name: String,
    ): Boolean {
        if (name.isBlank()) {
            return false
        }

        if (
            (isDefaultAccount(
                account = name.trim(),
            ))
        ) {
            errorData.update {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIError.ACCOUNT_EXISTS
                )
            }
            return false
        }

        val isValidData = accounts.find {
            it.name.trim().equalsIgnoringCase(
                other = name.trim(),
            )
        }.isNull()

        errorData.update {
            if (isValidData) {
                AddOrEditAccountScreenUIErrorData()
            } else {
                errorData.value.copy(
                    nameTextField = AddOrEditAccountScreenUIError.ACCOUNT_EXISTS
                )
            }
        }

        return isValidData
    }
}
