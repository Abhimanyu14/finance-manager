package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_source

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
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
        items = sources
            .map { source ->
                SelectSourceBottomSheetItemData(
                    isSelected = source.id == selectedSourceId,
                    icon = source.type.icon,
                    text = source.name,
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
    )
}
