package com.makeappssimple.abhimanyu.financemanager.entities.emoji

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmojiJsonEntity(
    override val character: String,
    override val codePoint: String,
    override val group: String,
    override val unicodeName: String,
) : Emoji
