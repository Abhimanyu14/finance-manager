package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Immutable
public data class TransactionsMenuBottomSheetEvents(
    val onSelectAllClick: () -> Unit,
    val onUpdateTransactionForClick: () -> Unit,
    val resetBottomSheetType: () -> Unit,
)

@Composable
public fun TransactionsMenuBottomSheet(
    modifier: Modifier = Modifier,
    // data: TransactionsMenuBottomSheetData,
    events: TransactionsMenuBottomSheetEvents,
) {
    val items = buildList {
        add(
            TransactionsMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_transactions_menu_update_transaction_for,
                ),
                onClick = events.onUpdateTransactionForClick,
            )
        )
        add(
            TransactionsMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_transactions_menu_select_all,
                ),
                onClick = {
                    events.onSelectAllClick()
                    events.resetBottomSheetType()
                },
            )
        )
    }

    TransactionsMenuBottomSheetUI(
        modifier = modifier,
        items = items,
    )
}
