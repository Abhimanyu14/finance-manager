package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.common

import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull

@Composable
public fun getDismissState(
    dismissedToEndAction: (() -> Unit)? = null,
    dismissedToStart: (() -> Unit)? = null,
    defaultAction: (() -> Unit)? = null,
): SwipeToDismissBoxState {
    return rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    if (dismissedToEndAction.isNotNull()) {
                        dismissedToEndAction()
                        true
                    } else {
                        false
                    }
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    if (dismissedToStart.isNotNull()) {
                        dismissedToStart()
                        true
                    } else {
                        false
                    }
                }

                SwipeToDismissBoxValue.Settled -> {
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
