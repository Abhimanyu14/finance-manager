package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel.EditAccountScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun EditAccountScreen(
    screenViewModel: EditAccountScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    // region view model data
    val accounts: ImmutableList<Account> by viewModel.accounts.collectAsStateWithLifecycle()
    val originalAccount: Account? by viewModel.originalAccount.collectAsStateWithLifecycle()
    val validAccountTypes: ImmutableList<AccountType> = viewModel.validAccountTypes
    // endregion

    val uiStateAndEvents = rememberEditAccountScreenUIStateAndEvents(
        accounts = accounts,
        originalAccount = originalAccount,
        validAccountTypes = validAccountTypes,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        EditAccountScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    LaunchedEffect(
        originalAccount,
    ) {
        originalAccount?.let { originalAccount ->
            uiStateAndEvents.events.setSelectedAccountTypeIndex(
                validAccountTypes.indexOf(
                    element = originalAccount.type,
                )
            )
            uiStateAndEvents.events.setName(
                uiStateAndEvents.state.name.copy(
                    text = originalAccount.name,
                )
            )
            uiStateAndEvents.events.setBalanceAmountValue(
                TextFieldValue(
                    text = originalAccount.balanceAmount.value.toString(),
                    selection = TextRange(originalAccount.balanceAmount.value.toString().length),
                )
            )
            originalAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
                uiStateAndEvents.events.setMinimumAccountBalanceAmountValue(
                    TextFieldValue(
                        text = minimumAccountBalanceAmount.value.toString(),
                        selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                    )
                )
            }
        }
    }

    EditAccountScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
