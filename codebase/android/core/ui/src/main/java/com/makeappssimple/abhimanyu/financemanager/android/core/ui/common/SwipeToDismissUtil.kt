package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull

@Composable
fun getDismissState(
    dismissedToEndAction: (() -> Unit)? = null,
    dismissedToStart: (() -> Unit)? = null,
    defaultAction: (() -> Unit)? = null,
): DismissState {
    return rememberDismissState(
        confirmValueChange = { dismissValue ->
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
