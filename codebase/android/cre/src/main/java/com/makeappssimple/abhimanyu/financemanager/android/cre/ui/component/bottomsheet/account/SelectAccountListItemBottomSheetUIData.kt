package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.accounts.AccountsListItemContentDataAndEventHandler

@Immutable
internal data class SelectAccountListItemBottomSheetUIData(
    @StringRes val titleTextStringResourceId: Int = 0,
    val data: List<AccountsListItemContentDataAndEventHandler> = emptyList(),
)
