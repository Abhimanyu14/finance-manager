package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.matchrowsize

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints

/**
 * Source - https://stackoverflow.com/a/72428903/9636037
 */
public fun Modifier.matchRowSize(): Modifier {
    return layout { measurable, constraints ->
        if (constraints.maxHeight == Constraints.Infinity) {
            layout(0, 0) {}
        } else {
            val placeable = measurable.measure(constraints)
            layout(placeable.width, placeable.height) {
                placeable.place(0, 0)
            }
        }
    }
}
