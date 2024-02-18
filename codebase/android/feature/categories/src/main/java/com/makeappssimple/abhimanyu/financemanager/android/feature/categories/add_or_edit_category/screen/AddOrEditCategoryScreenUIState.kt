package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Stable
class AddOrEditCategoryScreenUIState(
    data: MyResult<AddOrEditCategoryScreenUIData>?,
    private val unwrappedData: AddOrEditCategoryScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    isEdit: Boolean,
    val screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType,
    val setScreenBottomSheetType: (AddOrEditCategoryScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val isCtaButtonEnabled: Boolean = unwrappedData?.isCtaButtonEnabled.orFalse(),
    val selectedTransactionTypeIndex: Int? = unwrappedData?.selectedTransactionTypeIndex,
    val transactionTypesChipUIData: List<ChipUIData> =
        unwrappedData?.validTransactionTypes?.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }.orEmpty(),
    val emoji: String = unwrappedData?.emoji.orEmpty(),
    val emojiSearchText: String = unwrappedData?.emojiSearchText.orEmpty(),
    val title: TextFieldValue = unwrappedData?.title.orEmpty(),
    @StringRes
    val titleTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.titleTextFieldError?.textStringResourceId,
    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_appbar_title
    } else {
        R.string.screen_add_category_appbar_title
    },
    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_floating_action_button_content_description
    } else {
        R.string.screen_add_category_floating_action_button_content_description
    },
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AddOrEditCategoryScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberAddOrEditCategoryScreenUIState(
    data: MyResult<AddOrEditCategoryScreenUIData>?,
    isEdit: Boolean,
): AddOrEditCategoryScreenUIState {
    val (screenBottomSheetType, setScreenBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditCategoryScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
        setScreenBottomSheetType,
    ) {
        AddOrEditCategoryScreenUIState(
            data = data,
            isEdit = isEdit,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
