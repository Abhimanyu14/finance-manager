package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model

data class EmojiRemoteEntity(
    override val character: String,
    override val codePoint: String,
    override val group: String,
    override val unicodeName: String,
) : Emoji
