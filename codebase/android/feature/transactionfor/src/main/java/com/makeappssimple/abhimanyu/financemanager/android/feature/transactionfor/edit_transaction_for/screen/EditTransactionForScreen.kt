package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
fun EditTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside EditTransactionForScreen",
    )

    val screenUIData: AddOrEditTransactionForScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditTransactionForScreenUI(
        data = screenUIData?.copy(
            appBarTitleTextStringResourceId = R.string.screen_edit_transaction_for_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_for_floating_action_button_content_description,
        ) ?: AddOrEditTransactionForScreenUIData(
            appBarTitleTextStringResourceId = R.string.screen_edit_transaction_for_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_for_floating_action_button_content_description,
        ),
        events = AddOrEditTransactionForScreenUIEvents(
            clearTitle = screenViewModel::clearTitle,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::updateTransactionFor,
            updateTitle = screenViewModel::updateTitle,
        ),
        state = rememberCommonScreenUIState(),
    )
}
