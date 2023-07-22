package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType

val AccountType.icon: ImageVector
    get() = when (this) {
        AccountType.BANK -> {
            Icons.Rounded.AccountBalance
        }

        AccountType.CASH -> {
            Icons.Rounded.AttachMoney
        }

        AccountType.E_WALLET -> {
            Icons.Rounded.AccountBalanceWallet
        }
    }
