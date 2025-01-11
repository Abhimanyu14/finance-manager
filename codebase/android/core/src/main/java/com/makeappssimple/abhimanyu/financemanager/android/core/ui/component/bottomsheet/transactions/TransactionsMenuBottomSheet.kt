package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import kotlinx.collections.immutable.persistentListOf

@Composable
public fun TransactionsMenuBottomSheet(
    modifier: Modifier = Modifier,
    handleEvent: (event: TransactionsMenuBottomSheetEvent) -> Unit = {},
) {
    val items = persistentListOf(
        TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Edit,
            text = stringResource(
                id = R.string.bottom_sheet_transactions_menu_update_transaction_for,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick)
            },
        ),
        TransactionsMenuBottomSheetItemData(
            imageVector = MyIcons.Checklist,
            text = stringResource(
                id = R.string.bottom_sheet_transactions_menu_select_all_transactions,
            ),
            onClick = {
                handleEvent(TransactionsMenuBottomSheetEvent.OnSelectAllTransactionsClick)
            },
        )
    )

    TransactionsMenuBottomSheetUI(
        modifier = modifier,
        items = items,
    )
}
