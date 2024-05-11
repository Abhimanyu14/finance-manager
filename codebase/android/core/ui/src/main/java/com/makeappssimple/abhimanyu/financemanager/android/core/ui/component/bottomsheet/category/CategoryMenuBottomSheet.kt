package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Immutable
public data class CategoryMenuBottomSheetData(
    val isDeleteVisible: Boolean,
    val isEditVisible: Boolean,
    val isSetAsDefaultVisible: Boolean,
)

@Composable
public fun CategoryMenuBottomSheet(
    data: CategoryMenuBottomSheetData,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = buildList {
        if (data.isEditVisible) {
            add(
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
            add(
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
            add(
                CategoryMenuBottomSheetItemData(
                    imageVector = MyIcons.Delete,
                    text = stringResource(
                        id = R.string.bottom_sheet_category_menu_delete,
                    ),
                    onClick = onDeleteClick,
                )
            )
        }
    }

    CategoryMenuBottomSheetUI(
        items = items,
    )
}
