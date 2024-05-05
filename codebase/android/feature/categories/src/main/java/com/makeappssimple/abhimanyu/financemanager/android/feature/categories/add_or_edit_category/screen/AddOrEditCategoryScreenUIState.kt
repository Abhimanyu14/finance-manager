package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Stable
public class AddOrEditCategoryScreenUIState(
    public val screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType,
    public val isCtaButtonEnabled: Boolean,
    public val isLoading: Boolean,
    @StringRes
    public val appBarTitleTextStringResourceId: Int,
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int,
    @StringRes
    public val titleTextFieldErrorTextStringResourceId: Int?,
    public val selectedTransactionTypeIndex: Int?,
    public val transactionTypesChipUIData: List<ChipUIData>,
    public val emoji: String,
    public val emojiSearchText: String,
    public val title: TextFieldValue,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setScreenBottomSheetType: (AddOrEditCategoryScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
public fun rememberAddOrEditCategoryScreenUIState(
    data: MyResult<AddOrEditCategoryScreenUIData>?,
    isEdit: Boolean,
): AddOrEditCategoryScreenUIState {
    var screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddOrEditCategoryScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddOrEditCategoryScreenBottomSheetType: AddOrEditCategoryScreenBottomSheetType ->
            screenBottomSheetType = updatedAddOrEditCategoryScreenBottomSheetType
        }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: AddOrEditCategoryScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AddOrEditCategoryScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            isCtaButtonEnabled = unwrappedData?.isCtaButtonEnabled.orFalse(),
            selectedTransactionTypeIndex = unwrappedData?.selectedTransactionTypeIndex,
            transactionTypesChipUIData = unwrappedData?.validTransactionTypes?.map { transactionType ->
                ChipUIData(
                    text = transactionType.title,
                )
            }.orEmpty(),
            emoji = unwrappedData?.emoji.orEmpty(),
            emojiSearchText = unwrappedData?.emojiSearchText.orEmpty(),
            title = unwrappedData?.title.orEmpty(),
            titleTextFieldErrorTextStringResourceId = unwrappedData?.titleTextFieldError?.textStringResourceId,
            appBarTitleTextStringResourceId = if (isEdit) {
                R.string.screen_edit_category_appbar_title
            } else {
                R.string.screen_add_category_appbar_title
            },
            ctaButtonLabelTextStringResourceId = if (isEdit) {
                R.string.screen_edit_category_floating_action_button_content_description
            } else {
                R.string.screen_add_category_floating_action_button_content_description
            },
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(AddOrEditCategoryScreenBottomSheetType.None)
            },
        )
    }
}
