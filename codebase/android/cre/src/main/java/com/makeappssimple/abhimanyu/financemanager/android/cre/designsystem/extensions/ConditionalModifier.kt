package com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions

import androidx.compose.ui.Modifier

public fun Modifier.ifTrue(
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
