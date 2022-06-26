package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController

class AddSourceScreenViewState @OptIn(
    ExperimentalComposeUiApi::class,
) constructor(
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    val keyboardController: SoftwareKeyboardController?,
)

@OptIn(
    ExperimentalComposeUiApi::class,
)
@Composable
fun rememberAddSourceScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
) = remember {
    AddSourceScreenViewState(
        focusManager = focusManager,
        focusRequester = focusRequester,
        keyboardController = keyboardController,
    )
}
