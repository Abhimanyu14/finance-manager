package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_transaction_for

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUIData

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
    SelectListItemBottomSheetUI(
        modifier = modifier,
        data = SelectListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_transaction_for_title,
            items = data.transactionForValues
                .map { transactionFor ->
                    SelectListItemBottomSheetItemData(
                        text = transactionFor.titleToDisplay,
                        onClick = {
                            events.onItemClick(transactionFor)
                        },
                    )
                }
                .toList(),
        ),
    )
}
