package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModelImpl

@Composable
fun AddTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AddTransactionForScreen",
    )
    val title: TextFieldValue by screenViewModel.title.collectAsStateWithLifecycle()

    AddOrEditTransactionForScreenUI(
        data = AddOrEditTransactionForScreenUIData(
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
            title = title,
        ),
        events = AddOrEditTransactionForScreenUIEvents(
            clearTitle = screenViewModel::clearTitle,
            isValidTitle = screenViewModel::isValidTitle,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::insertTransactionFor,
            updateTitle = screenViewModel::updateTitle,
        ),
        state = rememberCommonScreenUIState(),
    )
}
