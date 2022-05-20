package com.makeappssimple.abhimanyu.financemanager.entities.emoji

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmojiLocalEntity(
    override val character: String,
    @Json(name = "code_point")
    override val codePoint: String,
    override val group: String,
    @Json(name = "unicode_name")
    override val unicodeName: String,
) : Emoji {
    constructor(
        emoji: Emoji,
    ) : this(
        character = emoji.character,
        codePoint = emoji.codePoint,
        group = emoji.group,
        unicodeName = emoji.unicodeName,
    )
}
