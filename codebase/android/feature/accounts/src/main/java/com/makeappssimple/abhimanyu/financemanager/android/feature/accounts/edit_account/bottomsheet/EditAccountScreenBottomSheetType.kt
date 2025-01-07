package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class EditAccountScreenBottomSheetType : ScreenBottomSheetType {
    data object None : EditAccountScreenBottomSheetType()
}
