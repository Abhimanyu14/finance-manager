package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData

@Stable
internal data class AddCategoryScreenUIState(
    val screenBottomSheetType: AddCategoryScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val isCtaButtonEnabled: Boolean,
    val isLoading: Boolean,
    val isSupportingTextVisible: Boolean,
    @StringRes val appBarTitleTextStringResourceId: Int,
    @StringRes val ctaButtonLabelTextStringResourceId: Int,
    @StringRes val titleTextFieldErrorTextStringResourceId: Int?,
    val selectedTransactionTypeIndex: Int?,
    val transactionTypesChipUIData: List<ChipUIData>,
    val emoji: String,
    val emojiSearchText: String,
    val title: TextFieldValue,
) : ScreenUIState
