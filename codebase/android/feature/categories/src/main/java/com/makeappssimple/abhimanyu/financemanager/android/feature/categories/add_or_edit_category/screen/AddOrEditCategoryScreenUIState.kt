package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Stable
class AddOrEditCategoryScreenUIState(
    data: AddOrEditCategoryScreenUIData,
    isEdit: Boolean,
    val addOrEditCategoryBottomSheetType: AddOrEditCategoryBottomSheetType,
    val setAddOrEditCategoryBottomSheetType: (AddOrEditCategoryBottomSheetType) -> Unit,
) {
    val isValidCategoryData: Boolean = data.isValidCategoryData
    val selectedTransactionTypeIndex: Int = data.selectedTransactionTypeIndex
    val emojiGroups: Map<String, List<Emoji>> = data.emojiGroups
    val transactionTypesChipUIData: List<ChipUIData> =
        data.transactionTypes.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }
    val emoji: String = data.emoji
    val searchText: String = data.searchText
    val title: TextFieldValue = data.title

    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_appbar_title
    } else {
        R.string.screen_add_category_appbar_title
    }

    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_floating_action_button_content_description
    } else {
        R.string.screen_add_category_floating_action_button_content_description
    }
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditCategoryBottomSheetType(AddOrEditCategoryBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditCategoryScreenUIState(
    data: AddOrEditCategoryScreenUIData,
    isEdit: Boolean,
): AddOrEditCategoryScreenUIState {
    val (addOrEditCategoryBottomSheetType, setAddOrEditCategoryBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditCategoryBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isEdit,
        addOrEditCategoryBottomSheetType,
        setAddOrEditCategoryBottomSheetType,
    ) {
        AddOrEditCategoryScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditCategoryBottomSheetType = addOrEditCategoryBottomSheetType,
            setAddOrEditCategoryBottomSheetType = setAddOrEditCategoryBottomSheetType,
        )
    }
}
