package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.CoroutineScope

class SettingsScreenViewState @OptIn(
    ExperimentalMaterialApi::class,
) constructor(
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val modalBottomSheetState: ModalBottomSheetState,
)

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun rememberSettingsScreenViewState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
) = remember {
    SettingsScreenViewState(
        coroutineScope = coroutineScope,
        focusManager = focusManager,
        modalBottomSheetState = modalBottomSheetState,
    )
}
