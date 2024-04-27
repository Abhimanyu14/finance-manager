package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AccountsScreenBottomSheetType : ScreenBottomSheetType {
    public data object DeleteConfirmation : AccountsScreenBottomSheetType()
    public data object None : AccountsScreenBottomSheetType()
    public data object SetAsDefaultConfirmation : AccountsScreenBottomSheetType()

    public data class Menu(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val accountId: Int,
    ) : AccountsScreenBottomSheetType()
}
