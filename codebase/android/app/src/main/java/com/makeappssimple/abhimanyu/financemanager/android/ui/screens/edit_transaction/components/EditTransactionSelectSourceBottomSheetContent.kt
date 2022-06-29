package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.components

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun EditTransactionSelectSourceBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    sources: List<Source>,
    selectedSourceId: Int?,
    resetBottomSheetType: () -> Unit,
    updateSource: (updatedSource: Source?) -> Unit,
) {
    EditTransactionSelectSourceBottomSheet(
        data = EditTransactionSelectSourceBottomSheetData(
            items = sources
                .map { source ->
                    EditTransactionSelectSourceBottomSheetItemData(
                        text = source.name,
                        iconKey = source.type.title,
                        isSelected = source.id == selectedSourceId,
                        onClick = {
                            toggleModalBottomSheetState(
                                coroutineScope = coroutineScope,
                                modalBottomSheetState = modalBottomSheetState,
                            ) {
                                updateSource(source)
                                resetBottomSheetType()
                            }
                        },
                    )
                }
                .toList(),
        ),
    )
}
