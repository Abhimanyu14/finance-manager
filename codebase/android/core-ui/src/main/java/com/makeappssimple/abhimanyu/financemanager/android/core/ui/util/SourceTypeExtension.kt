package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType

val SourceType.icon: ImageVector
    get() = when (this) {
        SourceType.BANK -> {
            Icons.Rounded.AccountBalance
        }

        SourceType.CASH -> {
            Icons.Rounded.AttachMoney
        }

        SourceType.E_WALLET -> {
            Icons.Rounded.AccountBalanceWallet
        }
    }
