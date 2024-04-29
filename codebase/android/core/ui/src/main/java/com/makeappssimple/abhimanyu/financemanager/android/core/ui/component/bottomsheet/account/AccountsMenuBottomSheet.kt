package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
public fun AccountsMenuBottomSheet(
    isDeleteVisible: Boolean,
    isEditVisible: Boolean,
    isSetAsDefaultVisible: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = buildList {
        if (isEditVisible) {
            add(
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
            add(
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
            add(
                AccountsMenuBottomSheetItemData(
                    imageVector = MyIcons.Delete,
                    text = stringResource(
                        id = R.string.bottom_sheet_accounts_menu_delete,
                    ),
                    onClick = onDeleteClick,
                )
            )
        }
    }

    AccountsMenuBottomSheetUI(
        items = items,
    )
}
