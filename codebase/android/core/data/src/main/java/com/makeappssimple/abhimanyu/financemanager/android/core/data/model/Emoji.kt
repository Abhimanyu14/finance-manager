package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji

fun Emoji.asEntity(): EmojiEntity {
    return EmojiEntity(
        codePoint = codePoint,
        group = group,
        character = character,
        unicodeName = unicodeName,
    )
}
