package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon

@Stable
class AddOrEditSourceScreenUIState(
    data: AddOrEditSourceScreenUIData,
    val addOrEditSourceBottomSheetType: AddOrEditSourceBottomSheetType,
    val nameTextFieldFocusRequester: FocusRequester,
    val balanceAmountTextFieldFocusRequester: FocusRequester,
    val updateBottomSheetType: (AddOrEditSourceBottomSheetType) -> Unit,
) {
    val isBalanceAmountTextFieldVisible: Boolean = data.visibilityData.balanceAmount
    val isCtaButtonEnabled: Boolean = data.isValidSourceData
    val isNameTextFieldVisible: Boolean = data.visibilityData.name
    val isNameTextFieldErrorTextVisible: Boolean = data.errorData.name.isNotNullOrBlank()
    val isSourceTypesRadioGroupVisible: Boolean = data.visibilityData.sourceTypes

    @StringRes
    val appBarTitleTextStringResourceId: Int = data.appBarTitleTextStringResourceId

    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = data.ctaButtonLabelTextStringResourceId
    val selectedSourceTypeIndex: Int = data.selectedSourceTypeIndex
    val sourceTypesChipUIDataList: List<ChipUIData> = data.sourceTypes
        .map { sourceType ->
            ChipUIData(
                text = sourceType.title,
                icon = sourceType.icon,
            )
        }
    val nameTextFieldErrorText: String = data.errorData.name.orEmpty()
    val balanceAmountValue: TextFieldValue = data.balanceAmountValue
    val name: TextFieldValue = data.name
    val resetBottomSheetType: () -> Unit = {
        updateBottomSheetType(AddOrEditSourceBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditSourceScreenUIState(
    data: AddOrEditSourceScreenUIData,
): AddOrEditSourceScreenUIState {
    var addOrEditSourceBottomSheetType by remember {
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
    val updateBottomSheetType: (AddOrEditSourceBottomSheetType) -> Unit =
        { updatedBottomSheetType ->
            addOrEditSourceBottomSheetType = updatedBottomSheetType
        }

    return remember(
        data,
        addOrEditSourceBottomSheetType,
        nameTextFieldFocusRequester,
        balanceAmountTextFieldFocusRequester,
    ) {
        AddOrEditSourceScreenUIState(
            data = data,
            addOrEditSourceBottomSheetType = addOrEditSourceBottomSheetType,
            nameTextFieldFocusRequester = nameTextFieldFocusRequester,
            balanceAmountTextFieldFocusRequester = balanceAmountTextFieldFocusRequester,
            updateBottomSheetType = updateBottomSheetType,
        )
    }
}
