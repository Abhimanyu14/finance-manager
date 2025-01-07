package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account

@Immutable
public sealed class SelectAccountBottomSheetEvent {
    public data object ResetBottomSheetType : SelectAccountBottomSheetEvent()
    public data class UpdateAccount(
        val updatedAccount: Account?,
    ) : SelectAccountBottomSheetEvent()
}
