package com.makeappssimple.abhimanyu.financemanager.android.ui.components.bottom_sheet.select_source

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.toggleModalBottomSheetState
import kotlinx.coroutines.CoroutineScope

@Composable
fun SelectSourceBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    sources: List<Source>,
    selectedSourceId: Int?,
    resetBottomSheetType: () -> Unit,
    updateSource: (updatedSource: Source?) -> Unit,
) {
    SelectSourceBottomSheet(
        data = SelectSourceBottomSheetData(
            items = sources
                .map { source ->
                    SelectSourceBottomSheetItemData(
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
