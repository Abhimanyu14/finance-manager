package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import kotlinx.collections.immutable.toImmutableList

@Composable
public fun CategoryMenuBottomSheet(
    data: CategoryMenuBottomSheetData,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = mutableListOf<CategoryMenuBottomSheetItemData>()
    if (data.isEditVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                imageVector = MyIcons.Edit,
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_edit,
                ),
                onClick = onEditClick,
            )
        )
    }
    if (data.isSetAsDefaultVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                imageVector = MyIcons.CheckCircle,
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_set_as_default_category,
                ),
                onClick = onSetAsDefaultClick,
            )
        )
    }
    if (data.isDeleteVisible) {
        items.add(
            CategoryMenuBottomSheetItemData(
                imageVector = MyIcons.Delete,
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_delete,
                ),
                onClick = onDeleteClick,
            )
        )
    }

    CategoryMenuBottomSheetUI(
        items = items.toImmutableList(),
    )
}
