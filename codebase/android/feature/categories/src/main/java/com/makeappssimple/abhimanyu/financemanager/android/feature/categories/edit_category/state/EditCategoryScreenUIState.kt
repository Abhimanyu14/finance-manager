package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class EditCategoryScreenUIState(
    val screenBottomSheetType: EditCategoryScreenBottomSheetType = EditCategoryScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isSupportingTextVisible: Boolean = false,
    val titleError: EditCategoryScreenTitleError = EditCategoryScreenTitleError.None,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState

internal sealed class EditCategoryScreenTitleError {
    data object CategoryExists : EditCategoryScreenTitleError()
    data object None : EditCategoryScreenTitleError()
}

internal val EditCategoryScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            EditCategoryScreenTitleError.CategoryExists -> R.string.screen_add_or_edit_category_error_category_exists
            EditCategoryScreenTitleError.None -> null
        }
    }
