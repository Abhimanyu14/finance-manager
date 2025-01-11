package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class AccountsScreenBottomSheetType : ScreenBottomSheetType {
    data object DeleteConfirmation : AccountsScreenBottomSheetType()
    data object None : AccountsScreenBottomSheetType()
    data object SetAsDefaultConfirmation : AccountsScreenBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val accountId: Int,
    ) : AccountsScreenBottomSheetType()
}
