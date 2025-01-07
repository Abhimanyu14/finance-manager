package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.emoji_circle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
public sealed class EmojiCircleSize(
    public val padding: Dp,
    public val textSize: Float,
) {
    public data object Small : EmojiCircleSize(
        padding = 2.dp,
        textSize = 20F,
    )

    public data object Normal : EmojiCircleSize(
        padding = 4.dp,
        textSize = 28F,
    )

    public data object Large : EmojiCircleSize(
        padding = 8.dp,
        textSize = 32F,
    )
}
