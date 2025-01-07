package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIEvent

@Immutable
internal sealed class AccountsScreenUIEvent : ScreenUIEvent {
    data object OnFloatingActionButtonClick : AccountsScreenUIEvent()
    data object OnNavigationBackButtonClick : AccountsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AccountsScreenUIEvent()

    sealed class OnAccountsDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : AccountsScreenUIEvent()
        data object PositiveButtonClick : AccountsScreenUIEvent()
    }

    sealed class OnAccountsMenuBottomSheet {
        data class DeleteButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()

        data class EditButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()

        data class SetAsDefaultButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()
    }

    sealed class OnAccountsSetAsDefaultConfirmationBottomSheet {
        data object NegativeButtonClick : AccountsScreenUIEvent()
        data object PositiveButtonClick : AccountsScreenUIEvent()
    }

    sealed class OnAccountsListItemContent {
        data class Click(
            val isDeleteEnabled: Boolean,
            val isDefault: Boolean,
            val accountId: Int?,
        ) : AccountsScreenUIEvent()
    }
}
