package com.makeappssimple.abhimanyu.financemanager.android

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    val myLogger: MyLogger,
    val navigationManager: NavigationManager,
) : ViewModel()
