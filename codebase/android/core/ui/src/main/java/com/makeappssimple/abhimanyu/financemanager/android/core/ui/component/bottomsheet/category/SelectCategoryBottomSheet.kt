package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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
            }
            .toImmutableList(),
    )
}
