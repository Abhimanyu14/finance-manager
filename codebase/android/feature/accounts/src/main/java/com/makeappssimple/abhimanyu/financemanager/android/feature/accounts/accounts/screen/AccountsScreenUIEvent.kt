package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AccountsScreenUIEvent : ScreenUIEvent {
    public data object OnFloatingActionButtonClick : AccountsScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AccountsScreenUIEvent()

    public sealed class OnAccountsDeleteConfirmationBottomSheet {
        public data object NegativeButtonClick : AccountsScreenUIEvent()
        public data object PositiveButtonClick : AccountsScreenUIEvent()
    }

    public sealed class OnAccountsMenuBottomSheet {
        public data class EditButtonClick(
            val accountId: Int,
        ) : AccountsScreenUIEvent()
    }

    public sealed class OnAccountsSetAsDefaultConfirmationBottomSheet {
        public data object NegativeButtonClick : AccountsScreenUIEvent()
        public data object PositiveButtonClick : AccountsScreenUIEvent()
    }
}
