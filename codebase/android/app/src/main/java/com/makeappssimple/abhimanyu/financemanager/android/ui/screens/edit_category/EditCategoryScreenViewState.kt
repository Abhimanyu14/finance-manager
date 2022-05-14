package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_category

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.CoroutineScope

class EditCategoryScreenViewState @OptIn(ExperimentalMaterialApi::class) constructor(
    val context: Context,
    val focusManager: FocusManager,
    val coroutineScope: CoroutineScope,
    val modalBottomSheetState: ModalBottomSheetState,
    val focusRequester: FocusRequester,
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberEditCategoryScreenViewState(
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    modalBottomSheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    ),
    focusRequester: FocusRequester = remember {
        FocusRequester()
    },
) = remember {
    EditCategoryScreenViewState(
        context = context,
        focusManager = focusManager,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
        focusRequester = focusRequester,
    )
}
