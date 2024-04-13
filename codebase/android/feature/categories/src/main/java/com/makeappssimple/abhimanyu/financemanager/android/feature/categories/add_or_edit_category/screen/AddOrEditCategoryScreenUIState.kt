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
public class AddOrEditCategoryScreenUIState(
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
    public val screenBottomSheetType: AddOrEditCategoryScreenBottomSheetType,
    public val setScreenBottomSheetType: (AddOrEditCategoryScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val isCtaButtonEnabled: Boolean = unwrappedData?.isCtaButtonEnabled.orFalse(),
    public val selectedTransactionTypeIndex: Int? = unwrappedData?.selectedTransactionTypeIndex,
    public val transactionTypesChipUIData: List<ChipUIData> =
        unwrappedData?.validTransactionTypes?.map { transactionType ->
            ChipUIData(
                text = transactionType.title,
            )
        }.orEmpty(),
    public val emoji: String = unwrappedData?.emoji.orEmpty(),
    public val emojiSearchText: String = unwrappedData?.emojiSearchText.orEmpty(),
    public val title: TextFieldValue = unwrappedData?.title.orEmpty(),
    @StringRes
    public val titleTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.titleTextFieldError?.textStringResourceId,
    @StringRes
    public val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_appbar_title
    } else {
        R.string.screen_add_category_appbar_title
    },
    @StringRes
    public val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_category_floating_action_button_content_description
    } else {
        R.string.screen_add_category_floating_action_button_content_description
    },
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AddOrEditCategoryScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
public fun rememberAddOrEditCategoryScreenUIState(
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
