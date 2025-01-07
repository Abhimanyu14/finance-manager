package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.save_button

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
public data class SaveButtonData(
    val isEnabled: Boolean,
    val isLoading: Boolean = false,
    @StringRes val textStringResourceId: Int,
)
