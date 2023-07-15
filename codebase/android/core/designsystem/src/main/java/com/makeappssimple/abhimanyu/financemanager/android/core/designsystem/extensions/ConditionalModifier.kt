package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions

import androidx.compose.ui.Modifier

fun Modifier.ifTrue(
    condition: Boolean,
    getModifier: () -> Modifier,
): Modifier {
    return this.then(
        other = if (condition) {
            getModifier()
        } else {
            this
        }
    )
}
