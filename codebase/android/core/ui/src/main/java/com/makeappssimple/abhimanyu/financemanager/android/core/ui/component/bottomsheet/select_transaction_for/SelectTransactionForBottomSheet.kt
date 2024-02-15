package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_transaction_for

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_account.SelectAccountBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_account.SelectAccountBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_account.SelectAccountBottomSheetEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item.SelectListItemBottomSheetItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item.SelectListItemBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.select_list_item.SelectListItemBottomSheetUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemDataAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.AccountsListItemEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.icon

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
            data = data.transactionForValues
                .map { transactionFor ->
                    // TODO-Abhi: Create a separate list item for transaction for
                    AccountsListItemDataAndEvents(
                        data = AccountsListItemData(
                            name = transactionFor.titleToDisplay,
                        ),
                        events = AccountsListItemEvents(
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
