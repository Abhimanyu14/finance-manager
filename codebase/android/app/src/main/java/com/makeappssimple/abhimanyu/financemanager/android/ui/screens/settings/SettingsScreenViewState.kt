package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

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

class SettingsScreenViewState @OptIn(ExperimentalMaterialApi::class) constructor(
    val focusManager: FocusManager,
    val coroutineScope: CoroutineScope,
    val modalBottomSheetState: ModalBottomSheetState,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberSettingsScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
) = remember {
    SettingsScreenViewState(
        focusManager = focusManager,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
    )
}
