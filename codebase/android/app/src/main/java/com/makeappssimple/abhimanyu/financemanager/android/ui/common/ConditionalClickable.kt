package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.conditionalClickable(
    onClick: (() -> Unit)? = null,
): Modifier {
    return if (onClick != null) {
        this.then(
            Modifier.clickable {
                onClick()
            }
        )
    } else {
        this
    }
}
