package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
public data class SelectCategoryBottomSheetData(
    val filteredCategories: ImmutableList<Category> = persistentListOf(),
    val selectedCategoryId: Int? = null,
)

@Immutable
public sealed class SelectCategoryBottomSheetEvent {
    public data object ResetBottomSheetType : SelectCategoryBottomSheetEvent()
    public data class UpdateCategory(
        val updatedCategory: Category,
    ) : SelectCategoryBottomSheetEvent()
}

@Composable
public fun SelectCategoryBottomSheet(
    modifier: Modifier = Modifier,
    data: SelectCategoryBottomSheetData,
    handleEvent: (event: SelectCategoryBottomSheetEvent) -> Unit = {},
) {
    SelectCategoryBottomSheetUI(
        modifier = modifier,
        items = data.filteredCategories
            .map { category ->
                SelectCategoryBottomSheetItemData(
                    category = category,
                    isSelected = category.id == data.selectedCategoryId,
                    onClick = {
                        handleEvent(
                            SelectCategoryBottomSheetEvent.UpdateCategory(
                                updatedCategory = category,
                            )
                        )
                        handleEvent(SelectCategoryBottomSheetEvent.ResetBottomSheetType)
                    },
                )
            },
    )
}
