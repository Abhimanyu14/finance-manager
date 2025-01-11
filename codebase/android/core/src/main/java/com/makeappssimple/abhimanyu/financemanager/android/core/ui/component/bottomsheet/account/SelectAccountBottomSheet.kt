package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentDataAndEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon

@Composable
public fun SelectAccountBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectAccountBottomSheetData,
    handleEvent: (event: SelectAccountBottomSheetEvent) -> Unit = {},
) {
    SelectAccountBottomSheetUI(
        modifier = modifier,
        data = SelectAccountListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_account_title,
            data = data.accounts
                .map { account ->
                    AccountsListItemContentDataAndEventHandler(
                        data = AccountsListItemContentData(
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isSelected = account.id == data.selectedAccountId,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        ),
                        handleEvent = { event ->
                            when (event) {
                                is AccountsListItemContentEvent.OnClick -> {
                                    handleEvent(
                                        SelectAccountBottomSheetEvent.UpdateAccount(
                                            updatedAccount = account,
                                        )
                                    )
                                    handleEvent(SelectAccountBottomSheetEvent.ResetBottomSheetType)
                                }
                            }
                        },
                    )
                }
                .toList(),
        ),
    )
}
