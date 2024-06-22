package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

internal sealed class AddAccountScreenBottomSheetType : ScreenBottomSheetType {
    data object None : AddAccountScreenBottomSheetType()
}
