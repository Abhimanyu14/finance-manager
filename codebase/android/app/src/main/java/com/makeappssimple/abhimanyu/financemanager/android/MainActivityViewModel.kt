package com.makeappssimple.abhimanyu.financemanager.android

import androidx.lifecycle.ViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    closeableCoroutineScope: CloseableCoroutineScope,
    val navigator: Navigator,
) : ViewModel(closeableCoroutineScope)
