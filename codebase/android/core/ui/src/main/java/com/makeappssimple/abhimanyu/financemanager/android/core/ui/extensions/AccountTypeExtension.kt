package com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions

import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons

val AccountType.icon: ImageVector
    get() = when (this) {
        AccountType.BANK -> {
            MyIcons.AccountBalance
        }

        AccountType.CASH -> {
            MyIcons.AttachMoney
        }

        AccountType.E_WALLET -> {
            MyIcons.AccountBalanceWallet
        }
    }
