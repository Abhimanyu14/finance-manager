package com.makeappssimple.abhimanyu.financemanager.android.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emoji_table")
data class EmojiLocalEntity(
    @PrimaryKey
    val character: String,
    @ColumnInfo(name = "code_point")
    val codePoint: String,
    val group: String,
    @ColumnInfo(name = "unicode_name")
    val unicodeName: String,
)
