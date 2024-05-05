package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
public fun TransactionForValuesMenuBottomSheet(
    isDeleteVisible: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val items = buildList {
        add(
            element = TransactionForValuesMenuBottomSheetItemData(
                imageVector = MyIcons.Edit,
                text = stringResource(
                    id = R.string.bottom_sheet_transaction_for_values_menu_edit,
                ),
                onClick = {
                    onEditClick()
                },
            ),
        )
        if (isDeleteVisible) {
            add(
                element = TransactionForValuesMenuBottomSheetItemData(
                    imageVector = MyIcons.Delete,
                    text = stringResource(
                        id = R.string.bottom_sheet_transaction_for_values_menu_delete,
                    ),
                    onClick = {
                        onDeleteClick()
                    },
                ),
            )
        }
    }

    TransactionForValuesMenuBottomSheetUI(
        items = items,
    )
}
