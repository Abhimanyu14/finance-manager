package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import android.content.Context
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

@Immutable
data class CommonScreenUIState(
    val context: Context,
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    val modalBottomSheetState: ModalBottomSheetState,
    val keyboardController: SoftwareKeyboardController?,
)

@Composable
fun rememberCommonScreenUIState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    ),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
): CommonScreenUIState {
    return remember {
        CommonScreenUIState(
            context = context,
            coroutineScope = coroutineScope,
            focusManager = focusManager,
            focusRequester = focusRequester,
            modalBottomSheetState = modalBottomSheetState,
            keyboardController = keyboardController,
        )
    }
}
