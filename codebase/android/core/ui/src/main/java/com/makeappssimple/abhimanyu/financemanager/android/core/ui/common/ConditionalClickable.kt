package com.makeappssimple.abhimanyu.financemanager.android.core.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.ui.Modifier

fun Modifier.conditionalClickable(
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
): Modifier {
    var result = this
    onClick?.let {
        result = if (onLongClick != null) {
            result.then(
                other = Modifier
                    .combinedClickable(
                        onClick = onClick,
                        onLongClick = onLongClick,
                    )
            )
        } else {
            result.then(
                other = Modifier
                    .clickable {
                        onClick()
                    }
            )
        }
    }
    return result
}
