package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts.AccountsListItemContentEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon

@Immutable
public data class SelectAccountBottomSheetData(
    val accounts: List<Account> = emptyList(),
    val selectedAccountId: Int? = null,
)

@Immutable
public sealed class SelectAccountBottomSheetEvent {
    public data object ResetBottomSheetType : SelectAccountBottomSheetEvent()
    public data class UpdateAccount(
        val updatedAccount: Account?,
    ) : SelectAccountBottomSheetEvent()
}

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
                    AccountsListItemContentDataAndEvents(
                        data = AccountsListItemContentData(
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isSelected = account.id == data.selectedAccountId,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        ),
                        events = AccountsListItemContentEvents(
                            onClick = {
                                handleEvent(
                                    SelectAccountBottomSheetEvent.UpdateAccount(
                                        updatedAccount = account,
                                    )
                                )
                                handleEvent(SelectAccountBottomSheetEvent.ResetBottomSheetType)
                            },
                        ),
                    )
                }
                .toList(),
        ),
    )
}
