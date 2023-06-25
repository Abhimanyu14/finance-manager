package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

@Stable
class AddOrEditSourceScreenUIState(
    data: MyResult<AddOrEditSourceScreenUIData>?,
    isEdit: Boolean,
    setAddOrEditSourceBottomSheetType: (AddOrEditSourceBottomSheetType) -> Unit,
    val addOrEditSourceBottomSheetType: AddOrEditSourceBottomSheetType,
    val nameTextFieldFocusRequester: FocusRequester,
    val balanceAmountTextFieldFocusRequester: FocusRequester,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull()
    val visibilityData = AddOrEditSourceScreenUIVisibilityData(
        balanceAmountTextField = isEdit,
        nameTextField = if (isEdit) {
            unwrappedData?.sourceIsNotCash.orFalse()
        } else {
            true
        },
        nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
        sourceTypesRadioGroup = if (isEdit) {
            unwrappedData?.sourceIsNotCash.orFalse()
        } else {
            true
        },
    )
    val isCtaButtonEnabled: Boolean = unwrappedData?.isValidSourceData.orFalse()

    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_source_appbar_title
    } else {
        R.string.screen_add_source_appbar_title
    }

    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_source_floating_action_button_content_description
    } else {
        R.string.screen_add_source_floating_action_button_content_description
    }

    @StringRes
    val nameTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.errorData?.nameTextField?.textStringResourceId

    val selectedSourceTypeIndex: Int = unwrappedData?.selectedSourceTypeIndex.orZero()
    val sourceTypesChipUIDataList: List<ChipUIData> = unwrappedData?.sourceTypes
        ?.map { sourceType ->
            ChipUIData(
                text = sourceType.title,
                icon = sourceType.icon,
            )
        }.orEmpty()

    val balanceAmountValue: TextFieldValue = unwrappedData?.balanceAmountValue.orEmpty()
    val name: TextFieldValue = unwrappedData?.name.orEmpty()
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditSourceBottomSheetType(AddOrEditSourceBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditSourceScreenUIState(
    data: MyResult<AddOrEditSourceScreenUIData>?,
    isEdit: Boolean,
): AddOrEditSourceScreenUIState {
    val (addOrEditSourceBottomSheetType, setAddOrEditSourceBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditSourceBottomSheetType.NONE,
        )
    }
    val nameTextFieldFocusRequester = remember {
        FocusRequester()
    }
    val balanceAmountTextFieldFocusRequester = remember {
        FocusRequester()
    }

    return remember(
        data,
        isEdit,
        addOrEditSourceBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        AddOrEditSourceScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditSourceBottomSheetType = addOrEditSourceBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            setAddOrEditSourceBottomSheetType = setAddOrEditSourceBottomSheetType,
        )
    }
}
