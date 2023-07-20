package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_account.screen

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
class AddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    isEdit: Boolean,
    setAddOrEditAccountBottomSheetType: (AddOrEditAccountBottomSheetType) -> Unit,
    val addOrEditAccountBottomSheetType: AddOrEditAccountBottomSheetType,
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
    val visibilityData = AddOrEditAccountScreenUIVisibilityData(
        balanceAmountTextField = isEdit,
        nameTextField = if (isEdit) {
            unwrappedData?.accountIsNotCash.orFalse()
        } else {
            true
        },
        nameTextFieldErrorText = unwrappedData?.errorData?.nameTextField.isNotNull(),
        accountTypesRadioGroup = if (isEdit) {
            unwrappedData?.accountIsNotCash.orFalse()
        } else {
            true
        },
    )
    val isCtaButtonEnabled: Boolean = unwrappedData?.isValidAccountData.orFalse()

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

    val selectedAccountTypeIndex: Int = unwrappedData?.selectedAccountTypeIndex.orZero()
    val accountTypesChipUIDataList: List<ChipUIData> = unwrappedData?.accountTypes
        ?.map { sourceType ->
            ChipUIData(
                text = sourceType.title,
                icon = sourceType.icon,
            )
        }.orEmpty()

    val balanceAmountValue: TextFieldValue = unwrappedData?.balanceAmountValue.orEmpty()
    val name: TextFieldValue = unwrappedData?.name.orEmpty()
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditAccountBottomSheetType(AddOrEditAccountBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditAccountScreenUIState(
    data: MyResult<AddOrEditAccountScreenUIData>?,
    isEdit: Boolean,
): AddOrEditAccountScreenUIState {
    val (addOrEditAccountBottomSheetType, setAddOrEditAccountBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditAccountBottomSheetType.NONE,
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
        addOrEditAccountBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        AddOrEditAccountScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditAccountBottomSheetType = addOrEditAccountBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            setAddOrEditAccountBottomSheetType = setAddOrEditAccountBottomSheetType,
        )
    }
}
