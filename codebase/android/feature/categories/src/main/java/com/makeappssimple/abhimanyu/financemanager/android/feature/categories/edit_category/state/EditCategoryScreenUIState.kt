package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
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
    @StringRes val appBarTitleTextStringResourceId: Int = -1,
    @StringRes val ctaButtonLabelTextStringResourceId: Int = -1,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int? = null,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState
