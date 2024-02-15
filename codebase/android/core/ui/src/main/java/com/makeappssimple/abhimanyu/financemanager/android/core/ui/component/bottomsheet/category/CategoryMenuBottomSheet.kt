package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Composable
fun CategoryMenuBottomSheet(
    isDeleteVisible: Boolean,
    isEditVisible: Boolean,
    isSetAsDefaultVisible: Boolean,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
) {
    val items = buildList {
        if (isEditVisible) {
            add(
                CategoryMenuBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_category_menu_edit,
                    ),
                    onClick = onEditClick,
                )
            )
        }
        if (isSetAsDefaultVisible) {
            add(
                CategoryMenuBottomSheetItemData(
                    text = stringResource(
                        id = R.string.bottom_sheet_category_menu_set_as_default_category,
                    ),
                    onClick = onSetAsDefaultClick,
                )
            )
        }
        if (isDeleteVisible) {
            add(
                CategoryMenuBottomSheetItemData(
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
