package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_transaction_for

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

@Composable
fun SelectTransactionForBottomSheet(
    modifier: Modifier = Modifier,
    transactionForValues: List<TransactionFor> = emptyList(),
    onItemClick: (TransactionFor) -> Unit = {},
) {
    SelectTransactionForBottomSheetUI(
        modifier = modifier,
        items = transactionForValues
            .map { transactionFor ->
                SelectTransactionForBottomSheetItemData(
                    text = transactionFor.titleToDisplay,
                    onClick = {
                        onItemClick(transactionFor)
                    },
                )
            }
            .toList(),
    )
}
