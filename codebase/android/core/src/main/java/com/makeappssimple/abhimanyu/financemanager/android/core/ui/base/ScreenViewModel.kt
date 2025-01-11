package com.makeappssimple.abhimanyu.financemanager.android.core.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope

public abstract class ScreenViewModel(
    viewModelScope: CoroutineScope,
) : ViewModel(
    viewModelScope = viewModelScope,
)
