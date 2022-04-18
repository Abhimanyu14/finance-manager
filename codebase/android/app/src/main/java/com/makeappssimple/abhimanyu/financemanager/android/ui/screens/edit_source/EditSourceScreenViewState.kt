package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.CoroutineScope

class EditSourceScreenViewState @OptIn(ExperimentalMaterialApi::class) constructor(
    val focusManager: FocusManager,
    val coroutineScope: CoroutineScope,
    val scaffoldState: ScaffoldState,
    val modalBottomSheetState: ModalBottomSheetState,
    val focusRequester: FocusRequester,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberEditSourceScreenViewState(
    focusManager: FocusManager = LocalFocusManager.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
) = remember {
    EditSourceScreenViewState(
        focusManager = focusManager,
        coroutineScope = coroutineScope,
        scaffoldState = scaffoldState,
        modalBottomSheetState = modalBottomSheetState,
        focusRequester = focusRequester,
    )
}