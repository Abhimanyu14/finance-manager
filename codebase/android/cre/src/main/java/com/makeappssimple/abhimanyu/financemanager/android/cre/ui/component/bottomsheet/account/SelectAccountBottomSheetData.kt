package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account

@Immutable
public data class SelectAccountBottomSheetData(
    val accounts: List<Account> = emptyList(),
    val selectedAccountId: Int? = null,
)
