package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable

@Composable
fun getDismissState(
    dismissedToEndAction: (() -> Unit)? = null,
    dismissedToStart: (() -> Unit)? = null,
    defaultAction: (() -> Unit)? = null,
): DismissState {
    return rememberDismissState(
        confirmStateChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToEnd -> {
                    if (dismissedToEndAction != null) {
                        dismissedToEndAction()
                        true
                    } else {
                        false
                    }
                }

                DismissValue.DismissedToStart -> {
                    if (dismissedToStart != null) {
                        dismissedToStart()
                        true
                    } else {
                        false
                    }
                }

                DismissValue.Default -> {
                    if (defaultAction != null) {
                        defaultAction()
                        true
                    } else {
                        false
                    }
                }
            }
        },
    )
}
