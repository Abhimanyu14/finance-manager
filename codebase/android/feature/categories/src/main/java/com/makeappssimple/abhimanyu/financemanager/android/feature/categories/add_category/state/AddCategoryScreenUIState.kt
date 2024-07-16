package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
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
    @StringRes val titleTextFieldErrorTextStringResourceId: Int? = null,
    val selectedTransactionTypeIndex: Int? = null,
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val emoji: String = "",
    val emojiSearchText: String = "",
    val title: TextFieldValue = TextFieldValue(),
) : ScreenUIState
