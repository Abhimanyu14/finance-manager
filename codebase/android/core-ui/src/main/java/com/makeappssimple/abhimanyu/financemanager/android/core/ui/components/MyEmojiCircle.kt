package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.conditionalClickable

sealed class EmojiCircleSize(
    val padding: Dp,
    val textSize: Float,
) {
    object Small : EmojiCircleSize(
        padding = 2.dp,
        textSize = 20F,
    )

    object Normal : EmojiCircleSize(
        padding = 4.dp,
        textSize = 28F,
    )

    object Large : EmojiCircleSize(
        padding = 8.dp,
        textSize = 32F,
    )
}

@Composable
fun MyEmojiCircle(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Transparent,
    emojiCircleSize: EmojiCircleSize = EmojiCircleSize.Small,
    emoji: String?,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .background(
                color = backgroundColor,
            )
            .padding(
                all = emojiCircleSize.padding,
            ),
    ) {
        AndroidView(
            factory = { context ->
                AppCompatTextView(context).apply {
                    setTextColor(Black.toArgb())
                    text = emoji ?: "😟"
                    textSize = emojiCircleSize.textSize
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            },
            update = {
                it.apply {
                    text = emoji ?: "😟"
                }
            },
        )
    }
}
