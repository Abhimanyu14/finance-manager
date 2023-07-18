package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
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

@Immutable
data class MyEmojiCircleData(
    val isLoading: Boolean = false,
    val backgroundColor: Color = Transparent,
    val emojiCircleSize: EmojiCircleSize = EmojiCircleSize.Small,
    val emoji: String = "",
)

@Immutable
data class MyEmojiCircleEvents(
    val onClick: (() -> Unit)? = null,
    val onLongClick: (() -> Unit)? = null,
)

@Composable
fun MyEmojiCircle(
    modifier: Modifier = Modifier,
    data: MyEmojiCircleData,
    events: MyEmojiCircleEvents = MyEmojiCircleEvents(),
) {
    if (data.isLoading) {
        MyEmojiCircleLoadingUI(
            modifier = modifier,
            emojiCircleSize = data.emojiCircleSize,
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(
                    shape = CircleShape,
                )
                .conditionalClickable(
                    onClick = events.onClick,
                    onLongClick = events.onLongClick,
                )
                .background(
                    color = data.backgroundColor,
                )
                .padding(
                    all = data.emojiCircleSize.padding,
                ),
        ) {
            AndroidView(
                factory = { context ->
                    AppCompatTextView(context).apply {
                        setTextColor(Black.toArgb())
                        text = data.emoji
                        textSize = data.emojiCircleSize.textSize
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }
                },
                update = {
                    it.apply {
                        text = data.emoji
                    }
                },
            )
        }
    }
}

@Composable
private fun MyEmojiCircleLoadingUI(
    modifier: Modifier = Modifier,
    emojiCircleSize: EmojiCircleSize,
) {
    Box(
        modifier = modifier
            .size(
                size = when (emojiCircleSize) {
                    EmojiCircleSize.Small -> {
                        28.dp
                    }

                    EmojiCircleSize.Normal -> {
                        38.dp
                    }

                    EmojiCircleSize.Large -> {
                        42.dp
                    }
                },
            )
            .clip(
                shape = CircleShape,
            )
            .shimmer(),
    )
}
