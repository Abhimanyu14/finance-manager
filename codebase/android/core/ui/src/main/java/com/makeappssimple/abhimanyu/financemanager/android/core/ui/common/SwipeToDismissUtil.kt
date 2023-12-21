package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.material3.SwipeToDismissState
import androidx.compose.material3.SwipeToDismissValue
import androidx.compose.material3.rememberSwipeToDismissState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull

@Composable
fun getDismissState(
    dismissedToEndAction: (() -> Unit)? = null,
    dismissedToStart: (() -> Unit)? = null,
    defaultAction: (() -> Unit)? = null,
): SwipeToDismissState {
    return rememberSwipeToDismissState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissValue.StartToEnd -> {
                    if (dismissedToEndAction.isNotNull()) {
                        dismissedToEndAction()
                        true
                    } else {
                        false
                    }
                }

                SwipeToDismissValue.EndToStart -> {
                    if (dismissedToStart.isNotNull()) {
                        dismissedToStart()
                        true
                    } else {
                        false
                    }
                }

                SwipeToDismissValue.Settled -> {
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
