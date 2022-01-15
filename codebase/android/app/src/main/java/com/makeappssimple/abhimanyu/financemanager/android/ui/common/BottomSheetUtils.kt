package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
fun toggleModalBottomSheetState(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    action: () -> Unit,
) {
    coroutineScope.launch {
        if (!modalBottomSheetState.isAnimationRunning) {
            if (modalBottomSheetState.isVisible) {
                modalBottomSheetState.hide()
            } else {
                modalBottomSheetState.show()
            }
        }
        action()
    }
}
