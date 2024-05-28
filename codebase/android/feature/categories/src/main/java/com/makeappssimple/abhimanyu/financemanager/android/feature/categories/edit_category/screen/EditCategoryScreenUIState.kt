package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class EditCategoryScreenUIState(
    val screenBottomSheetType: EditCategoryScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val isSupportingTextVisible: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int?,
    val selectedTransactionTypeIndex: Int?,
    val transactionTypesChipUIData: ImmutableList<ChipUIData>,
    val emoji: String,
    val emojiSearchText: String,
    val title: TextFieldValue,
) : ScreenUIState
