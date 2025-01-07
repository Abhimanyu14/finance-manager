package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.emoji_circle

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyEmojiCircleEvent {
    public data object OnClick : MyEmojiCircleEvent()
}
