package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class AddCategoryScreenUIState(
    val screenBottomSheetType: AddCategoryScreenBottomSheetType = AddCategoryScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val isSupportingTextVisible: Boolean = false,
    val titleError: AddCategoryScreenTitleError = AddCategoryScreenTitleError.None,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState

public sealed class AddCategoryScreenTitleError {
    public data object CategoryExists : AddCategoryScreenTitleError()
    public data object None : AddCategoryScreenTitleError()
}

internal val AddCategoryScreenTitleError.stringResourceId: Int?
    get() {
        return when (this) {
            AddCategoryScreenTitleError.CategoryExists -> R.string.screen_add_or_edit_category_error_category_exists
            AddCategoryScreenTitleError.None -> null
        }
    }
