package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "emoji_table")
data class EmojiLocalEntity(
    @PrimaryKey
    override val character: String,

    @ColumnInfo(name = "code_point")
    @Json(name = "code_point")
    override val codePoint: String,

    override val group: String,

    @ColumnInfo(name = "unicode_name")
    @Json(name = "unicode_name")
    override val unicodeName: String,
) : Emoji
