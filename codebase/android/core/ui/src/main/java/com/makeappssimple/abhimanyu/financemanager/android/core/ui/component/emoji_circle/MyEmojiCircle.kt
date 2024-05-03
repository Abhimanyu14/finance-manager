package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle

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
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

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

@Immutable
public data class MyEmojiCircleData(
    val isLoading: Boolean = false,
    val backgroundColor: Color = Transparent,
    val emojiCircleSize: EmojiCircleSize = EmojiCircleSize.Small,
    val emoji: String = "",
)

@Immutable
public data class MyEmojiCircleEvents(
    val onClick: (() -> Unit)? = null,
    val onLongClick: (() -> Unit)? = null,
)

@Composable
public fun MyEmojiCircle(
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
            MyText(
                text = data.emoji,
                style = TextStyle(
                    fontSize = data.emojiCircleSize.textSize.sp,
                    platformStyle = PlatformTextStyle(
                        emojiSupportMatch = EmojiSupportMatch.None
                    ),
                    textAlign = TextAlign.Center,
                )
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
                    is EmojiCircleSize.Small -> {
                        28.dp
                    }

                    is EmojiCircleSize.Normal -> {
                        38.dp
                    }

                    is EmojiCircleSize.Large -> {
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
