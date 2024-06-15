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
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditAccountScreen",
    )

    // region view model data
    val accounts: ImmutableList<Account> by screenViewModel.accounts.collectAsStateWithLifecycle()
    val originalAccount: Account? by screenViewModel.originalAccount.collectAsStateWithLifecycle()
    val validAccountTypes: ImmutableList<AccountType> = screenViewModel.validAccountTypes
    // endregion

    val uiStateAndStateEvents = rememberEditAccountScreenUIStateAndEvents(
        accounts = accounts,
        originalAccount = originalAccount,
        validAccountTypes = validAccountTypes,
    )
    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
    ) {
        EditAccountScreenUIEventHandler(
            viewModel = screenViewModel,
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    LaunchedEffect(
        originalAccount,
    ) {
        originalAccount?.let { originalAccount ->
            uiStateAndStateEvents.events.setSelectedAccountTypeIndex(
                validAccountTypes.indexOf(
                    element = originalAccount.type,
                )
            )
            uiStateAndStateEvents.events.setName(
                uiStateAndStateEvents.state.name.copy(
                    text = originalAccount.name,
                )
            )
            uiStateAndStateEvents.events.setBalanceAmountValue(
                TextFieldValue(
                    text = originalAccount.balanceAmount.value.toString(),
                    selection = TextRange(originalAccount.balanceAmount.value.toString().length),
                )
            )
            originalAccount.minimumAccountBalanceAmount?.let { minimumAccountBalanceAmount ->
                uiStateAndStateEvents.events.setMinimumAccountBalanceAmountValue(
                    TextFieldValue(
                        text = minimumAccountBalanceAmount.value.toString(),
                        selection = TextRange(minimumAccountBalanceAmount.value.toString().length),
                    )
                )
            }
        }
    }

    EditAccountScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
