package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
public fun TransactionForValuesMenuBottomSheet(
    isDeleteVisible: Boolean,
    transactionForId: Int,
    navigateToEditTransactionForScreen: (transactionForId: Int) -> Unit,
    onDeleteClick: () -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    val items = buildList {
        add(
            element = TransactionForValuesMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_transaction_for_values_menu_edit,
                ),
                onClick = {
                    resetBottomSheetType()
                    navigateToEditTransactionForScreen(transactionForId)
                },
            ),
        )
        if (isDeleteVisible) {
            add(
                element = TransactionForValuesMenuBottomSheetItemData(
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
