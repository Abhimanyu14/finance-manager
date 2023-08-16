package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import emoji.core.model.NetworkEmoji

fun Emoji.asEntity(): EmojiEntity {
    return EmojiEntity(
        codePoint = codePoint,
        group = group,
        character = character,
        unicodeName = unicodeName,
    )
}

fun NetworkEmoji.asEmoji(): Emoji {
    return Emoji(
        character = this.character,
        codePoint = this.codePoint,
        group = this.group,
        unicodeName = this.unicodeName,
    )
}
