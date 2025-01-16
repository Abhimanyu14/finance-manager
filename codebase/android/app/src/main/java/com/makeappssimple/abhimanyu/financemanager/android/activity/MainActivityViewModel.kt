package com.makeappssimple.abhimanyu.financemanager.android.activity

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    val logKit: LogKit,
    val navigationKit: NavigationKit,
) : ViewModel()
