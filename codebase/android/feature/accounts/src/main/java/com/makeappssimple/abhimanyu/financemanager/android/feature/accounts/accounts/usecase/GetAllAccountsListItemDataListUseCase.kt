package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.usecase

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts.AccountsListItemHeaderData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.icon
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.isDefaultAccount
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

public class GetAllAccountsListItemDataListUseCase @Inject constructor(
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase,
) {
    public suspend operator fun invoke(
        allAccounts: ImmutableList<Account>,
        defaultAccountId: Int?,
    ): ImmutableList<AccountsListItemData> {
        val updatedAccountsListItemDataList = mutableListOf<AccountsListItemData>()
        val allAccountTypes = AccountType.entries.sortedBy {
            it.sortOrder
        }
        val allAccountsGroupedByType = allAccounts.groupBy {
            it.type
        }
        allAccountTypes.forEach { accountType ->
            if (allAccountsGroupedByType[accountType].isNotNull()) {
                updatedAccountsListItemDataList.add(
                    AccountsListItemHeaderData(
                        isHeading = true,
                        balance = "",
                        name = accountType.title,
                    )
                )
                updatedAccountsListItemDataList.addAll(
                    allAccountsGroupedByType[accountType]?.sortedByDescending { account ->
                        account.balanceAmount.value
                    }?.map { account ->
                        val isDeleteEnabled = !checkIfAccountIsUsedInTransactionsUseCase(
                            accountId = account.id,
                        )
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
                            ) && isDeleteEnabled,
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
        return updatedAccountsListItemDataList.toImmutableList()
    }
}
