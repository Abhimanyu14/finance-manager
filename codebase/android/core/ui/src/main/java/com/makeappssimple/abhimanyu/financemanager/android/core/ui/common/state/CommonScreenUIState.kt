package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.state

import android.content.Context
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
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
public data class CommonScreenUIState(
    val context: Context,
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    val modalBottomSheetState: SheetState,
    val keyboardController: SoftwareKeyboardController?,
)

@Composable
public fun rememberCommonScreenUIState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
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
