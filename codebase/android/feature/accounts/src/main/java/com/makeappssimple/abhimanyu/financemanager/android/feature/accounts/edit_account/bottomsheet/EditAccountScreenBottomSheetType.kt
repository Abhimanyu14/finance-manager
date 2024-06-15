package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class EditAccountScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : EditAccountScreenBottomSheetType()
}
