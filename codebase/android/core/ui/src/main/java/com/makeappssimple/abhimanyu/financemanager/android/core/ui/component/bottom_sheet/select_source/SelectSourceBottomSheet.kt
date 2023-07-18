package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item.SelectListItemBottomSheetUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon

@Immutable
data class SelectSourceBottomSheetData(
    val sources: List<Source> = emptyList(),
    val selectedSourceId: Int? = null,
)

@Immutable
data class SelectSourceBottomSheetEvents(
    val resetBottomSheetType: () -> Unit = {},
    val updateSource: (Source?) -> Unit = {},
)

@Composable
fun SelectSourceBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectSourceBottomSheetData,
    events: SelectSourceBottomSheetEvents,
) {
    SelectListItemBottomSheetUI(
        modifier = modifier,
        data = SelectListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_source_title,
            items = data.sources
                .map { source ->
                    SelectListItemBottomSheetItemData(
                        isSelected = source.id == data.selectedSourceId,
                        icon = source.type.icon,
                        text = source.name,
                        onClick = {
                            events.updateSource(source)
                            events.resetBottomSheetType()
                        },
                    )
                }
                .toList(),
        ),
    )
}
