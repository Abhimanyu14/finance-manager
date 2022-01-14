package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val navigationManager: NavigationManager,
) : ViewModel()
