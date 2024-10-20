package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
public fun BottomSheetHandler(
    isBottomSheetVisible: Boolean,
    screenBottomSheetType: ScreenBottomSheetType,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    keyboardController: SoftwareKeyboardController?,
) {
    BottomSheetTypeChangeHandler(
        showModalBottomSheet = isBottomSheetVisible,
        screenBottomSheetType = screenBottomSheetType,
        coroutineScope = coroutineScope,
        modalBottomSheetState = modalBottomSheetState,
        keyboardController = keyboardController,
    )
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
        coroutineScope.launch {
            if (showModalBottomSheet) {
                keyboardController?.hide()
                showModalBottomSheet(
                    modalBottomSheetState = modalBottomSheetState,
                )
            } else {
                hideModalBottomSheet(
                    modalBottomSheetState = modalBottomSheetState,
                )
            }
        }
    }
}

@Composable
internal fun BottomSheetBackHandler(
    isEnabled: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: SheetState,
    onBottomSheetDismiss: () -> Unit,
) {
    BackHandler(
        enabled = isEnabled,
    ) {
        coroutineScope.launch {
            hideModalBottomSheet(
                modalBottomSheetState = modalBottomSheetState,
                onBottomSheetDismiss = onBottomSheetDismiss,
            )
        }
    }
}

private suspend fun showModalBottomSheet(
    modalBottomSheetState: SheetState,
) {
    if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
        modalBottomSheetState.show()
    }
}

private suspend fun hideModalBottomSheet(
    modalBottomSheetState: SheetState,
    onBottomSheetDismiss: () -> Unit = {},
) {
    if (modalBottomSheetState.currentValue == modalBottomSheetState.targetValue) {
        modalBottomSheetState.hide()
        onBottomSheetDismiss()
    }
}
