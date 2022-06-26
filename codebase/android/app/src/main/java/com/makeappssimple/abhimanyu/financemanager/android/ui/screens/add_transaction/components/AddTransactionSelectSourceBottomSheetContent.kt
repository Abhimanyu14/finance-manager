package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun AddTransactionSelectSourceBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    sources: List<Source>,
    selectedSourceId: Int?,
    resetBottomSheetType: () -> Unit,
    updateSource: (updatedSource: Source?) -> Unit,
) {
    AddTransactionSelectSourceBottomSheet(
        data = AddTransactionSelectSourceBottomSheetData(
            items = sources
                .map { source ->
                    AddTransactionSelectSourceBottomSheetItemData(
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
