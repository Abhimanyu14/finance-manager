package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AccountsScreenUIEvent : ScreenUIEvent {
    public data object OnFloatingActionButtonClick : AccountsScreenUIEvent()
    public data object OnNavigationBackButtonClick : AccountsScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AccountsScreenUIEvent()

    public sealed class OnAccountsDeleteConfirmationBottomSheet {
        public data object NegativeButtonClick : AccountsScreenUIEvent()
        public data object PositiveButtonClick : AccountsScreenUIEvent()
    }

    public sealed class OnAccountsMenuBottomSheet {
        public data class DeleteButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()

        public data class EditButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()

        public data class SetAsDefaultButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()
    }

    public sealed class OnAccountsSetAsDefaultConfirmationBottomSheet {
        public data object NegativeButtonClick : AccountsScreenUIEvent()
        public data object PositiveButtonClick : AccountsScreenUIEvent()
    }

    public sealed class OnAccountsListItemContent {
        public data class Click(
            val isDeleteEnabled: Boolean,
            val isDefault: Boolean,
            val accountId: Int?,
        ) : AccountsScreenUIEvent()
    }
}