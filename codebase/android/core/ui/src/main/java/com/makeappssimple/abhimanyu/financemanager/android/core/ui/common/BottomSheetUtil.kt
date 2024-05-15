package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
public fun BottomSheetHandler(
    showModalBottomSheet: Boolean,
    screenBottomSheetType: ScreenBottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
    resetBottomSheetType: () -> Unit,
) {
    BottomSheetDisposeHandler(
        modalBottomSheetState = modalBottomSheetState,
        resetBottomSheetType = resetBottomSheetType,
    )

    BottomSheetTypeChangeHandler(
        showModalBottomSheet = showModalBottomSheet,
        screenBottomSheetType = screenBottomSheetType,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
        keyboardController = keyboardController,
    )
}

@Composable
private fun BottomSheetDisposeHandler(
    modalBottomSheetState: SheetState,
    resetBottomSheetType: () -> Unit,
) {
    if (modalBottomSheetState.currentValue != SheetValue.Hidden) {
        DisposableEffect(
            key1 = resetBottomSheetType,
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
    screenBottomSheetType: ScreenBottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
) {
    LaunchedEffect(
        key1 = screenBottomSheetType,
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
    isEnabled: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    resetBottomSheetType: () -> Unit,
) {
    BackHandler(
        enabled = isEnabled,
    ) {
        hideModalBottomSheet(
            coroutineScope = coroutineScope,
            modalBottomSheetState = modalBottomSheetState,
            action = resetBottomSheetType,
        )
    }
}

private fun showModalBottomSheet(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
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
    modalBottomSheetState: SheetState,
    action: (() -> Unit)? = null,
) {
    coroutineScope.launch {
        if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
            modalBottomSheetState.hide()
            action?.invoke()
        }
    }
}
