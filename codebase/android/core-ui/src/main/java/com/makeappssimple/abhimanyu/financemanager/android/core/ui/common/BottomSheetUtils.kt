package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.activity.compose.BackHandler
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun toggleModalBottomSheetState(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    action: (() -> Unit)? = null,
) {
    coroutineScope.launch {
        if (!modalBottomSheetState.isAnimationRunning) {
            if (modalBottomSheetState.isVisible) {
                modalBottomSheetState.hide()
            } else {
                modalBottomSheetState.show()
            }
        }
        action?.invoke()
    }
}

@Composable
fun BottomSheetBackHandler(
    enabled: Boolean,
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    resetBottomSheetType: () -> Unit,
) {
    BackHandler(
        enabled = enabled,
    ) {
        toggleModalBottomSheetState(
            coroutineScope = coroutineScope,
            modalBottomSheetState = modalBottomSheetState,
        ) {
            resetBottomSheetType()
        }
    }
}
