package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.emoji_circle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.extensions.shimmer.shimmer

@Composable
public fun MyEmojiCircle(
    modifier: Modifier = Modifier,
    data: MyEmojiCircleData,
    handleEvent: (event: MyEmojiCircleEvent) -> Unit = {},
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
                    onClick = if (data.isClickable) {
                        {
                            handleEvent(MyEmojiCircleEvent.OnClick)
                        }
                    } else {
                        null
                    },
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
