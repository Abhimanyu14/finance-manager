package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModelImpl

@Composable
fun EditTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside EditTransactionForScreen",
    )
    val title: TextFieldValue by screenViewModel.title.collectAsStateWithLifecycle()

    AddOrEditTransactionForScreenView(
        data = AddOrEditTransactionForScreenViewData(
            appBarTitleTextStringResourceId = R.string.screen_edit_transaction_for_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_transaction_for_floating_action_button_content_description,
            navigationManager = screenViewModel.navigationManager,
            title = title,
            clearTitle = screenViewModel::clearTitle,
            isValidTitle = screenViewModel::isValidTitle,
            onCtaButtonClick = screenViewModel::updateTransactionFor,
            updateTitle = screenViewModel::updateTitle,
        ),
        state = rememberCommonScreenViewState(),
    )
}
