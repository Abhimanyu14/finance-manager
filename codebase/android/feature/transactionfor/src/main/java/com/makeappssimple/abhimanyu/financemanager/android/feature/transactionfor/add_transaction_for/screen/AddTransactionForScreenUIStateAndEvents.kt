package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Stable
internal class AddTransactionForScreenUIStateAndEvents(
    val state: AddTransactionForScreenUIState,
    val events: AddTransactionForScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AddTransactionForScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddTransactionForScreenUIStateAndEvents(
    data: MyResult<AddTransactionForScreenUIData>?,
): AddTransactionForScreenUIStateAndEvents {
    var screenBottomSheetType: AddTransactionForScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddTransactionForScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddTransactionForScreenBottomSheetType: AddTransactionForScreenBottomSheetType ->
            screenBottomSheetType = updatedAddTransactionForScreenBottomSheetType
        }

    return remember(
        data,
        screenBottomSheetType,
    ) {
        val unwrappedData: AddTransactionForScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }

        // TODO(Abhi): Can be reordered to match the class ordering
        AddTransactionForScreenUIStateAndEvents(
            state = AddTransactionForScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
                isLoading = unwrappedData.isNull(),
                isCtaButtonEnabled = unwrappedData?.isValidTransactionForData,
                appBarTitleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
                title = unwrappedData?.title,
                titleTextFieldErrorTextStringResourceId = unwrappedData?.titleTextFieldError?.textStringResourceId,
            ),
            events = AddTransactionForScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AddTransactionForScreenBottomSheetType.None)
                },
            ),
        )
    }
}
