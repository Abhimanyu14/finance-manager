package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor.TransactionForListItemEvent

@Immutable
public data class SelectTransactionForBottomSheetData(
    val transactionForValues: List<TransactionFor> = emptyList(),
)

@Immutable
public data class SelectTransactionForBottomSheetEvents(
    val onItemClick: (TransactionFor) -> Unit = {},
)

@Composable
public fun SelectTransactionForBottomSheet(
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
                        handleEvent = {
                            when (it) {
                                is TransactionForListItemEvent.OnClick -> {
                                    events.onItemClick(transactionFor)
                                }

                                is TransactionForListItemEvent.OnMoreOptionsIconButtonClick -> {}
                            }
                        },
                    )
                }
                .toList(),
        ),
    )
}
