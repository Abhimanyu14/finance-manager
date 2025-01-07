package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
public data class MyReadOnlyTextFieldData(
    val isLoading: Boolean = false,
    val value: String,
    @StringRes val labelTextStringResourceId: Int,
)
