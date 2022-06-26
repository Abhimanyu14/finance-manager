package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

class CategoriesScreenViewState @OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
) constructor(
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val keyboardController: SoftwareKeyboardController?,
    val modalBottomSheetState: ModalBottomSheetState,
)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun rememberCategoriesScreenViewState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
) = remember {
    CategoriesScreenViewState(
        coroutineScope = coroutineScope,
        focusManager = focusManager,
        keyboardController = keyboardController,
        modalBottomSheetState = modalBottomSheetState,
    )
}
