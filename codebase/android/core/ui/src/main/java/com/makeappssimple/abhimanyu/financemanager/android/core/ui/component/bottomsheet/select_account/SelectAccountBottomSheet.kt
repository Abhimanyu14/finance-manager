package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item.SelectListItemBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item.SelectListItemBottomSheetUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon

@Immutable
data class SelectAccountBottomSheetData(
    val accounts: List<Account> = emptyList(),
    val selectedAccountId: Int? = null,
)

@Immutable
data class SelectAccountBottomSheetEvents(
    val resetBottomSheetType: () -> Unit = {},
    val updateAccount: (Account?) -> Unit = {},
)

@Composable
fun SelectAccountBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectAccountBottomSheetData,
    events: SelectAccountBottomSheetEvents,
) {
    SelectListItemBottomSheetUI(
        modifier = modifier,
        data = SelectListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_account_title,
            data = data.accounts
                .map { account ->
                    AccountsListItemDataAndEvents(
                        data = AccountsListItemData(
                            isLowBalance = account.balanceAmount < account.minimumAccountBalanceAmount.orEmpty(),
                            isSelected = account.id == data.selectedAccountId,
                            icon = account.type.icon,
                            accountId = account.id,
                            balance = account.balanceAmount.toString(),
                            name = account.name,
                        ),
                        events = AccountsListItemEvents(
                            onClick = {
                                events.updateAccount(account)
                                events.resetBottomSheetType()
                            },
                        ),
                    )

                }
                .toList(),
        ),
    )
}
