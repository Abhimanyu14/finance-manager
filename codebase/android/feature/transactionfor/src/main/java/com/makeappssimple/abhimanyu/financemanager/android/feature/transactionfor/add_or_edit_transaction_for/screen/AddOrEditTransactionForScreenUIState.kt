package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Stable
class AddOrEditTransactionForScreenUIState(
    data: AddOrEditTransactionForScreenUIData,
    isEdit: Boolean,
    setAddOrEditTransactionForBottomSheetType: (AddOrEditTransactionForBottomSheetType) -> Unit,
    val addOrEditTransactionForBottomSheetType: AddOrEditTransactionForBottomSheetType,
) {
    val isCtaButtonEnabled: Boolean = data.isValidTransactionForData

    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_for_appbar_title
    } else {
        R.string.screen_add_transaction_for_appbar_title
    }

    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_for_floating_action_button_content_description
    } else {
        R.string.screen_add_transaction_for_floating_action_button_content_description
    }

    val title: TextFieldValue = data.title
    val resetBottomSheetType: () -> Unit = {
        setAddOrEditTransactionForBottomSheetType(AddOrEditTransactionForBottomSheetType.NONE)
    }
}

@Composable
fun rememberAddOrEditTransactionForScreenUIState(
    data: AddOrEditTransactionForScreenUIData,
    isEdit: Boolean,
): AddOrEditTransactionForScreenUIState {
    val (addOrEditTransactionForBottomSheetType, setAddOrEditTransactionForBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditTransactionForBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isEdit,
        addOrEditTransactionForBottomSheetType,
    ) {
        AddOrEditTransactionForScreenUIState(
            data = data,
            isEdit = isEdit,
            addOrEditTransactionForBottomSheetType = addOrEditTransactionForBottomSheetType,
            setAddOrEditTransactionForBottomSheetType = setAddOrEditTransactionForBottomSheetType,
        )
    }
}
