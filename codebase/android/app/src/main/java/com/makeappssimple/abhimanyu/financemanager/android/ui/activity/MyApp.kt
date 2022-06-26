package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
fun MyApp() {
    val viewModelStoreOwner = checkNotNull(
        value = LocalViewModelStoreOwner.current,
    ) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val activityViewModel = hiltViewModel<MainActivityViewModel>(
        viewModelStoreOwner = viewModelStoreOwner,
    )

    MyAppView(
        activityViewModel = activityViewModel,
    )
}
