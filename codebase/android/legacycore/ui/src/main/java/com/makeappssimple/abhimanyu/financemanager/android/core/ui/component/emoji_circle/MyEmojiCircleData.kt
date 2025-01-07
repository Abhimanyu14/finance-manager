package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.emoji_circle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
public data class MyEmojiCircleData(
    val isClickable: Boolean = false,
    val isLoading: Boolean = false,
    val backgroundColor: Color = Color.Transparent,
    val emojiCircleSize: EmojiCircleSize = EmojiCircleSize.Small,
    val emoji: String = "",
)
