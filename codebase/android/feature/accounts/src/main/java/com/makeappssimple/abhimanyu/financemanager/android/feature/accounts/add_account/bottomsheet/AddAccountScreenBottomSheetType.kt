package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddAccountScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddAccountScreenBottomSheetType()
}
