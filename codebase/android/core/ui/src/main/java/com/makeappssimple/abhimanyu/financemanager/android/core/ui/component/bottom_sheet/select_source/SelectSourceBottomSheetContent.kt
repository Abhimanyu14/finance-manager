package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon

@Composable
fun SelectSourceBottomSheetContent(
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
                        updateSource(source)
                        resetBottomSheetType()
                    },
                )
            }
            .toList(),
    )
}
