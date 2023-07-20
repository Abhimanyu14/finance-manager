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
data class SelectAccountBottomSheetData(
    val accounts: List<Source> = emptyList(),
    val selectedAccountId: Int? = null,
)

@Immutable
data class SelectAccountBottomSheetEvents(
    val resetBottomSheetType: () -> Unit = {},
    val updateAccount: (Source?) -> Unit = {},
)

@Composable
fun SelectAccountBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectAccountBottomSheetData,
    events: SelectAccountBottomSheetEvents,
) {
    SelectListItemBottomSheetUI(
        modifier = modifier,
        data = SelectListItemBottomSheetUIData(
            titleTextStringResourceId = R.string.bottom_sheet_select_source_title,
            items = data.accounts
                .map { source ->
                    SelectListItemBottomSheetItemData(
                        isSelected = source.id == data.selectedAccountId,
                        icon = source.type.icon,
                        text = source.name,
                        onClick = {
                            events.updateAccount(source)
                            events.resetBottomSheetType()
                        },
                    )
                }
                .toList(),
        ),
    )
}
