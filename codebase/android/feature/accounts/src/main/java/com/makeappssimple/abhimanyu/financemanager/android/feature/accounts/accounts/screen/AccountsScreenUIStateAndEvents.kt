package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount

@Stable
internal class AccountsScreenUIStateAndEvents(
    val state: AccountsScreenUIState,
    val events: AccountsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Immutable
internal data class AccountsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setAccountIdToDelete: (Int?) -> Unit,
    val setClickedItemId: (Int?) -> Unit,
    val setScreenBottomSheetType: (AccountsScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAccountsScreenUIStateAndEvents(
    defaultAccountId: Int?,
    allAccounts: List<Account>,
    isAccountUsedInTransactions: Map<Int, Boolean>,
    accountsTotalBalanceAmountValue: Long,
    accountsTotalMinimumBalanceAmountValue: Long,
): AccountsScreenUIStateAndEvents {
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var accountIdToDelete: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    var screenBottomSheetType: AccountsScreenBottomSheetType by remember {
        mutableStateOf(
            value = AccountsScreenBottomSheetType.None,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    val setAccountIdToDelete = { updatedAccountIdToDelete: Int? ->
        accountIdToDelete = updatedAccountIdToDelete
    }
    val setScreenBottomSheetType =
        { updatedAccountsBottomSheetType: AccountsScreenBottomSheetType ->
            screenBottomSheetType = updatedAccountsBottomSheetType
        }

    return remember(
        clickedItemId,
        accountIdToDelete,
        screenBottomSheetType,
        setClickedItemId,
        setAccountIdToDelete,
        setScreenBottomSheetType,
        accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue,
        allAccounts,
    ) {
        val accountTypes = AccountType.entries.sortedBy {
            it.sortOrder
        }
        val groupedAccounts = allAccounts.groupBy {
            it.type
        }
        val accountsListItemDataList = mutableListOf<AccountsListItemData>()
        accountTypes.forEach { accountType ->
            if (groupedAccounts[accountType].isNotNull()) {
                accountsListItemDataList.add(
                    AccountsListItemHeaderData(
                        isHeading = true,
                        balance = "",
                        name = accountType.title,
                    )
                )
                accountsListItemDataList.addAll(
                    groupedAccounts[accountType]?.sortedByDescending { account ->
                        account.balanceAmount.value
                    }?.map { account ->
                        val deleteEnabled = isAccountUsedInTransactions[account.id] != true
                        val isDefault = if (defaultAccountId.isNull()) {
                            isDefaultAccount(
                                account = account.name,
                            )
                        } else {
                            defaultAccountId == account.id
                        }

                        AccountsListItemContentData(
                            isDefault = isDefault,
                            isDeleteEnabled = !isDefaultAccount(
                                account = account.name,
                            ) && deleteEnabled,
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isMoreOptionsIconButtonVisible = true,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        )
                    }.orEmpty()
                )
            }
        }

        AccountsScreenUIStateAndEvents(
            state = AccountsScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AccountsScreenBottomSheetType.None,
                clickedItemId = clickedItemId,
                accountIdToDelete = accountIdToDelete,
                isLoading = allAccounts.isEmpty(),
                accountsListItemDataList = accountsListItemDataList.orEmpty(),
                accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.orZero(),
            ),
            events = AccountsScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setClickedItemId = setClickedItemId,
                setAccountIdToDelete = setAccountIdToDelete,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AccountsScreenBottomSheetType.None)
                },
            ),
        )
    }
}
