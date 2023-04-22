package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull

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
                    if (dismissedToEndAction.isNotNull()) {
                        dismissedToEndAction()
                        true
                    } else {
                        false
                    }
                }

                DismissValue.DismissedToStart -> {
                    if (dismissedToStart.isNotNull()) {
                        dismissedToStart()
                        true
                    } else {
                        false
                    }
                }

                DismissValue.Default -> {
                    if (defaultAction.isNotNull()) {
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
