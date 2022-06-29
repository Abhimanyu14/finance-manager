package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.screen

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

class SettingsScreenViewState constructor(
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val keyboardController: SoftwareKeyboardController?,
    val modalBottomSheetState: ModalBottomSheetState,
)

@Composable
fun rememberSettingsScreenViewState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
) = remember {
    SettingsScreenViewState(
        coroutineScope = coroutineScope,
        focusManager = focusManager,
        keyboardController = keyboardController,
        modalBottomSheetState = modalBottomSheetState,
    )
}
