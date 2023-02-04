package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModelImpl

@Composable
fun AddTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddTransactionForScreen",
    )
    val name: String by screenViewModel.name.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddOrEditTransactionForScreenView(
        data = AddOrEditTransactionForScreenViewData(
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_for_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_for_floating_action_button_content_description,
            navigationManager = screenViewModel.navigationManager,
            name = name,
            clearName = screenViewModel::clearName,
            isValidName = screenViewModel::isValidName,
            onCtaButtonClick = screenViewModel::insertTransactionFor,
            updateName = { updatedName ->
                screenViewModel.updateName(
                    updatedName = updatedName,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
