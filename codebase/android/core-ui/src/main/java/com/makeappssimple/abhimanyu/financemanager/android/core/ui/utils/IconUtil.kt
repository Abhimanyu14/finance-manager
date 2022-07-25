package com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalance
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType

fun getIcon(
    name: String,
): ImageVector {
    return when (name) {
        SourceType.BANK.title -> {
            Icons.Rounded.AccountBalance
        }
        SourceType.CASH.title -> {
            Icons.Rounded.AttachMoney
        }
        SourceType.E_WALLET.title -> {
            Icons.Rounded.AccountBalanceWallet
        }
        else -> {
            Icons.Rounded.Menu
        }
    }
}
