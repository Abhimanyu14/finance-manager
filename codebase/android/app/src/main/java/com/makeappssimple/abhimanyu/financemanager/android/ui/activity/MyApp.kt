package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@ExperimentalMaterial3Api
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
