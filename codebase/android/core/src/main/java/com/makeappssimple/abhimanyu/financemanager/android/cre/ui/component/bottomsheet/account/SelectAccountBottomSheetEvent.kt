package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Account

@Immutable
public sealed class SelectAccountBottomSheetEvent {
    public data object ResetBottomSheetType : SelectAccountBottomSheetEvent()
    public data class UpdateAccount(
        val updatedAccount: Account?,
    ) : SelectAccountBottomSheetEvent()
}
