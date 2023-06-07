package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModelImpl

@Composable
fun AddTransactionScreen(
    screenViewModel: AddOrEditTransactionScreenViewModel = hiltViewModel<AddOrEditTransactionScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AddTransactionScreen",
    )

    val screenUIData: AddOrEditTransactionScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditTransactionScreenUI(
        data = screenUIData?.copy(
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
        ) ?: AddOrEditTransactionScreenUIData(
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
        ),
        events = AddOrEditTransactionScreenUIEvents(
            clearAmount = screenViewModel::clearAmount,
            clearDescription = screenViewModel::clearDescription,
            clearTitle = screenViewModel::clearTitle,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::insertTransaction,
            updateAmount = screenViewModel::updateAmount,
            updateCategory = screenViewModel::updateCategory,
            updateDescription = screenViewModel::updateDescription,
            updateSelectedTransactionForIndex = screenViewModel::updateSelectedTransactionForIndex,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateSourceFrom = screenViewModel::updateSourceFrom,
            updateSourceTo = screenViewModel::updateSourceTo,
            updateTitle = screenViewModel::updateTitle,
            updateTransactionDate = screenViewModel::updateTransactionDate,
            updateTransactionTime = screenViewModel::updateTransactionTime,
        ),
        state = rememberCommonScreenUIState(),
    )
}
