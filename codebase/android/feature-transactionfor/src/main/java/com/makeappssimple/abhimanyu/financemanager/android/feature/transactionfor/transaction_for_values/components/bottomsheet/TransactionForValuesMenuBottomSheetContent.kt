package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.components.bottomsheet

import android.widget.Toast
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun TransactionForValuesMenuBottomSheetContent(
    isDeleteVisible: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    navigationManager: NavigationManager,
    onDeleteClick: () -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    val context = LocalContext.current
    val items = mutableListOf<TransactionForValuesMenuBottomSheetItemData>()
    items.add(
        element = TransactionForValuesMenuBottomSheetItemData(
            text = stringResource(
                id = R.string.bottom_sheet_transaction_for_values_menu_edit,
            ),
            onClick = {
                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                ) {
                    resetBottomSheetType()
                    // TODO-Abhi: Add Navigation to Edit Transaction For screen
                    // TODO-Abhi: To Implement - Edit Transaction For
                    Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_SHORT).show()
                }
            },
        ),
    )
    if (isDeleteVisible) {
        items.add(
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

    TransactionForValuesMenuBottomSheet(
        items = items,
    )
}
