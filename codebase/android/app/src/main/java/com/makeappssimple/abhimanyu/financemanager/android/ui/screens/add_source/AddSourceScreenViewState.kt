package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager

class AddSourceScreenViewState constructor(
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
)

@Composable
fun rememberAddSourceScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
) = remember {
    AddSourceScreenViewState(
        focusManager = focusManager,
        focusRequester = focusRequester,
    )
}
