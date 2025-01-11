package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import kotlinx.collections.immutable.toImmutableList

@Composable
public fun AccountsMenuBottomSheet(
    isDeleteVisible: Boolean,
    isEditVisible: Boolean,
    isSetAsDefaultVisible: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = mutableListOf<AccountsMenuBottomSheetItemData>()
    if (isEditVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.Edit,
                text = stringResource(
                    id = R.string.bottom_sheet_accounts_menu_edit,
                ),
                onClick = onEditClick,
            )
        )
    }
    if (isSetAsDefaultVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.CheckCircle,
                text = stringResource(
                    id = R.string.bottom_sheet_accounts_menu_set_as_default_account,
                ),
                onClick = onSetAsDefaultClick,
            )
        )
    }
    if (isDeleteVisible) {
        items.add(
            AccountsMenuBottomSheetItemData(
                imageVector = MyIcons.Delete,
                text = stringResource(
                    id = R.string.bottom_sheet_accounts_menu_delete,
                ),
                onClick = onDeleteClick,
            )
        )
    }

    AccountsMenuBottomSheetUI(
        items = items.toImmutableList(),
    )
}
