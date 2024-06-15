package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.equalsIgnoringCase
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import kotlinx.collections.immutable.ImmutableList

@Stable
internal class AddTransactionForScreenUIStateAndEvents(
    val state: AddTransactionForScreenUIState,
    val events: AddTransactionForScreenUIStateEvents,
) : ScreenUIStateAndStateEvents

@Stable
internal class AddTransactionForScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setTitle: (updatedTitle: TextFieldValue) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAddTransactionForScreenUIStateAndEvents(
    transactionForValues: ImmutableList<TransactionFor>,
): AddTransactionForScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: AddTransactionForScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddTransactionForScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddTransactionForScreenBottomSheetType: AddTransactionForScreenBottomSheetType ->
            screenBottomSheetType = updatedAddTransactionForScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(AddTransactionForScreenBottomSheetType.None)
    }
    // endregion

    // region title
    var title: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setTitle = { updatedTitle: TextFieldValue ->
        title = updatedTitle
    }
    // endregion

    // region title text field error text string resource id
    var titleTextFieldErrorTextStringResourceId: Int? by remember {
        mutableStateOf(
            value = null,
        )
    }
    // endregion

    return remember(
        screenBottomSheetType,
        title,
        transactionForValues,
    ) {
        titleTextFieldErrorTextStringResourceId = null
        val isCtaButtonEnabled = if (title.text.isBlank()) {
            false
        } else if (transactionForValues.find {
                it.title.equalsIgnoringCase(
                    other = title.text.trim(),
                )
            }.isNotNull()
        ) {
            titleTextFieldErrorTextStringResourceId =
                R.string.screen_add_or_edit_transaction_for_error_exists
            false
        } else {
            true
        }

        AddTransactionForScreenUIStateAndEvents(
            state = AddTransactionForScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AddTransactionForScreenBottomSheetType.None,
                isLoading = transactionForValues.isEmpty(),
                isCtaButtonEnabled = isCtaButtonEnabled,
                appBarTitleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
                title = title,
                titleTextFieldErrorTextStringResourceId = titleTextFieldErrorTextStringResourceId,
            ),
            events = AddTransactionForScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setTitle = setTitle,
            ),
        )
    }
}
