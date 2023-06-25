package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
internal fun MyApp() {
    val viewModelStoreOwner = checkNotNull(
        value = LocalViewModelStoreOwner.current,
    ) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val activityViewModel = hiltViewModel<MainActivityViewModel>(
        viewModelStoreOwner = viewModelStoreOwner,
    )

    MyAppUI(
        activityViewModel = activityViewModel,
    )
}
