package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Immutable
public sealed class TransactionsMenuBottomSheetEvent {
    public data object OnSelectAllTransactionsClick : TransactionsMenuBottomSheetEvent()
    public data object OnUpdateTransactionForClick : TransactionsMenuBottomSheetEvent()
}

@Composable
public fun TransactionsMenuBottomSheet(
    modifier: Modifier = Modifier,
    // data: TransactionsMenuBottomSheetData,
    handleEvent: (event: TransactionsMenuBottomSheetEvent) -> Unit = {},
) {
    val items = buildList {
        add(
            TransactionsMenuBottomSheetItemData(
                imageVector = MyIcons.Edit,
                text = stringResource(
                    id = R.string.bottom_sheet_transactions_menu_update_transaction_for,
                ),
                onClick = {
                    handleEvent(TransactionsMenuBottomSheetEvent.OnUpdateTransactionForClick)
                },
            )
        )
        add(
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
    }

    TransactionsMenuBottomSheetUI(
        modifier = modifier,
        items = items,
    )
}
