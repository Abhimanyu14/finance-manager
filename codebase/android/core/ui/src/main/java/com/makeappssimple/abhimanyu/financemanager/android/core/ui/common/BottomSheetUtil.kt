package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.BottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomSheetHandler(
    showModalBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController?,
    resetBottomSheetType: () -> Unit,
) {
    BottomSheetDisposeHandler(
        modalBottomSheetState = modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    BottomSheetTypeChangeHandler(
        showModalBottomSheet = showModalBottomSheet,
        bottomSheetType = bottomSheetType,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
        keyboardController = keyboardController,
    )
}

@Composable
private fun BottomSheetDisposeHandler(
    modalBottomSheetState: ModalBottomSheetState,
    resetBottomSheetType: () -> Unit,
) {
    if (modalBottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
        DisposableEffect(
            key1 = Unit,
        ) {
            onDispose {
                resetBottomSheetType()
            }
        }
    }
}

@Composable
private fun BottomSheetTypeChangeHandler(
    showModalBottomSheet: Boolean,
    bottomSheetType: BottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    keyboardController: SoftwareKeyboardController?,
) {
    LaunchedEffect(
        key1 = bottomSheetType,
    ) {
        if (showModalBottomSheet) {
            keyboardController?.hide()
            showModalBottomSheet(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            )
        } else {
            hideModalBottomSheet(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            )
        }
    }
}

@Composable
internal fun BottomSheetBackHandler(
    enabled: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    resetBottomSheetType: () -> Unit,
) {
    BackHandler(
        enabled = enabled,
    ) {
        hideModalBottomSheet(
            coroutineScope = coroutineScope,
            modalBottomSheetState = modalBottomSheetState,
        ) {
            resetBottomSheetType()
        }
    }
}

private fun showModalBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    action: (() -> Unit)? = null,
) {
    coroutineScope.launch {
        if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
            modalBottomSheetState.show()
            action?.invoke()
        }
    }
}

private fun hideModalBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    action: (() -> Unit)? = null,
) {
    coroutineScope.launch {
        if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
            modalBottomSheetState.hide()
            action?.invoke()
        }
    }
}
