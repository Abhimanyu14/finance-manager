package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUIData
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
            items = data.accounts
                .map { account ->
                    SelectListItemBottomSheetItemData(
                        isSelected = account.id == data.selectedAccountId,
                        icon = account.type.icon,
                        primaryText = account.name,
                        secondaryText = account.balanceAmount.toSignedString(),
                        onClick = {
                            events.updateAccount(account)
                            events.resetBottomSheetType()
                        },
                    )
                }
                .toList(),
        ),
    )
}
