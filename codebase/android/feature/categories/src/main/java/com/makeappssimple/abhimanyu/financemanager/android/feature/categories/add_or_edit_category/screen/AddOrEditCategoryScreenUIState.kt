package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Stable
internal class AddOrEditCategoryScreenUIState(
    val screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType,
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
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (AddOrEditCategoryScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
internal fun rememberAddOrEditCategoryScreenUIState(
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
            isBottomSheetVisible = screenBottomSheetType != AddOrEditCategoryScreenBottomSheetType.None,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            isSupportingTextVisible = unwrappedData?.titleTextFieldError?.textStringResourceId.isNotNull(),
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
