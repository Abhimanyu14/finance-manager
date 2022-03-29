package com.makeappssimple.abhimanyu.financemanager.android.models.emoji

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emoji_table")
data class EmojiLocalEntity(
    @PrimaryKey
    override val character: String,
    @ColumnInfo(name = "code_point")
    override val codePoint: String,
    override val group: String,
    @ColumnInfo(name = "unicode_name")
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
