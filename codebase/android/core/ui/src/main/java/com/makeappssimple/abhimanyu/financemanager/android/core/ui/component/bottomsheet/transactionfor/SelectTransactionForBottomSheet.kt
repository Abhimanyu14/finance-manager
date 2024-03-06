package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionForListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.TransactionForListItemEvents

@Immutable
data class SelectTransactionForBottomSheetData(
    val transactionForValues: List<TransactionFor> = emptyList(),
)

@Immutable
data class SelectTransactionForBottomSheetEvents(
    val onItemClick: (TransactionFor) -> Unit = {},
)

@Composable
fun SelectTransactionForBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectTransactionForBottomSheetData,
    events: SelectTransactionForBottomSheetEvents,
) {
    SelectTransactionForBottomSheetUI(
        modifier = modifier,
        data = SelectTransactionForListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_transaction_for_title,
            data = data.transactionForValues
                .map { transactionFor ->
                    TransactionForListItemDataAndEvents(
                        data = TransactionForListItemData(
                            title = transactionFor.titleToDisplay,
                        ),
                        events = TransactionForListItemEvents(
                            onClick = {
                                events.onItemClick(transactionFor)
                            },
                        ),
                    )
                }
                .toList(),
        ),
    )
}
