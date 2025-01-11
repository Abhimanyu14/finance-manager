package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.backup_card

import androidx.compose.runtime.Immutable

@Immutable
public sealed class BackupCardEvent {
    public data object OnClick : BackupCardEvent()
}
