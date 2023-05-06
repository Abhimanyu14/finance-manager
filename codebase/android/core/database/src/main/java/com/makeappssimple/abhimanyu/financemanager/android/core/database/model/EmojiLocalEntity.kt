package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "emoji_table")
data class EmojiLocalEntity(
    @PrimaryKey
    override val character: String,

    @ColumnInfo(name = "code_point")
    @SerialName(value = "code_point")
    override val codePoint: String,

    override val group: String,

    @ColumnInfo(name = "unicode_name")
    @SerialName(value = "unicode_name")
    override val unicodeName: String,
) : Emoji
