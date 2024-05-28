package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Composable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
internal class AccountsScreenUIStateAndEvents(
    val state: AccountsScreenUIState,
    val events: AccountsScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberAccountsScreenUIStateAndEvents(
    defaultAccountId: Int?,
    allAccounts: ImmutableList<Account>,
    isAccountUsedInTransactions: Map<Int, Boolean>,
    accountsTotalBalanceAmountValue: Long,
    accountsTotalMinimumBalanceAmountValue: Long,
): AccountsScreenUIStateAndEvents {
    // region clicked item id
    var clickedItemId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setClickedItemId = { updatedClickedItemId: Int? ->
        clickedItemId = updatedClickedItemId
    }
    // endregion

    // region screen bottom sheet type
    var screenBottomSheetType: AccountsScreenBottomSheetType by remember {
        mutableStateOf(
            value = AccountsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAccountsBottomSheetType: AccountsScreenBottomSheetType ->
            screenBottomSheetType = updatedAccountsBottomSheetType
        }
    // endregion

    return remember(
        clickedItemId,
        setClickedItemId,
        screenBottomSheetType,
        setScreenBottomSheetType,
        defaultAccountId,
        allAccounts,
        isAccountUsedInTransactions,
        accountsTotalBalanceAmountValue,
        accountsTotalMinimumBalanceAmountValue,
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
                isLoading = allAccounts.isEmpty(),
                accountsListItemDataList = accountsListItemDataList.toImmutableList(),
                accountsTotalBalanceAmountValue = accountsTotalBalanceAmountValue.orZero(),
                accountsTotalMinimumBalanceAmountValue = accountsTotalMinimumBalanceAmountValue.orZero(),
            ),
            events = AccountsScreenUIStateEvents(
                setScreenBottomSheetType = setScreenBottomSheetType,
                setClickedItemId = setClickedItemId,
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AccountsScreenBottomSheetType.None)
                },
            ),
        )
    }
}
