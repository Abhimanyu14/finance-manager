package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import kotlinx.coroutines.CoroutineScope

class EditTransactionScreenViewState @OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
) constructor(
    val context: Context,
    val coroutineScope: CoroutineScope,
    val focusManager: FocusManager,
    val focusRequester: FocusRequester,
    val keyboardController: SoftwareKeyboardController?,
    val modalBottomSheetState: ModalBottomSheetState,
)

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun rememberEditTransactionScreenViewState(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
) = remember {
    EditTransactionScreenViewState(
        context = context,
        coroutineScope = coroutineScope,
        focusManager = focusManager,
        focusRequester = focusRequester,
        keyboardController = keyboardController,
        modalBottomSheetState = modalBottomSheetState,
    )
}
