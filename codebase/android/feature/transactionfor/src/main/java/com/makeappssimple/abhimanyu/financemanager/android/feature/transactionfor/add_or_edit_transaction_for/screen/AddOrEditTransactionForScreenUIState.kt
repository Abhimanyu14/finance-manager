package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Stable
class AddOrEditTransactionForScreenUIState(
    data: MyResult<AddOrEditTransactionForScreenUIData>?,
    private val unwrappedData: AddOrEditTransactionForScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    isEdit: Boolean,
    setScreenBottomSheetType: (AddOrEditTransactionForScreenBottomSheetType) -> Unit,
    val screenBottomSheetType: AddOrEditTransactionForScreenBottomSheetType,
    val isLoading: Boolean = unwrappedData.isNull(),
    val isCtaButtonEnabled: Boolean? = unwrappedData?.isValidTransactionForData,
    @StringRes
    val appBarTitleTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_for_appbar_title
    } else {
        R.string.screen_add_transaction_for_appbar_title
    },
    @StringRes
    val ctaButtonLabelTextStringResourceId: Int = if (isEdit) {
        R.string.screen_edit_transaction_for_floating_action_button_content_description
    } else {
        R.string.screen_add_transaction_for_floating_action_button_content_description
    },
    val title: TextFieldValue? = unwrappedData?.title,
    val titleTextFieldErrorTextStringResourceId: Int? =
        unwrappedData?.titleTextFieldError?.textStringResourceId,
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AddOrEditTransactionForScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberAddOrEditTransactionForScreenUIState(
    data: MyResult<AddOrEditTransactionForScreenUIData>?,
    isEdit: Boolean,
): AddOrEditTransactionForScreenUIState {
    val (screenBottomSheetType, setScreenBottomSheetType) = remember {
        mutableStateOf(
            value = AddOrEditTransactionForScreenBottomSheetType.NONE,
        )
    }

    return remember(
        data,
        isEdit,
        screenBottomSheetType,
    ) {
        AddOrEditTransactionForScreenUIState(
            data = data,
            isEdit = isEdit,
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
