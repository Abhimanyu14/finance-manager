package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddOrEditAccountScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddOrEditAccountScreenBottomSheetType()
}
