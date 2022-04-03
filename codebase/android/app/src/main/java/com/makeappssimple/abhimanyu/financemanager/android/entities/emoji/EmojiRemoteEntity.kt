package com.makeappssimple.abhimanyu.financemanager.android.entities.emoji

data class EmojiRemoteEntity(
    override val character: String,
    override val codePoint: String,
    override val group: String,
    override val unicodeName: String,
) : Emoji
