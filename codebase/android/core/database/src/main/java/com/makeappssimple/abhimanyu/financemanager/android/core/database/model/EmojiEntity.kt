package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "emoji_table")
data class EmojiEntity(
    @PrimaryKey
    val character: String,

    @ColumnInfo(name = "code_point")
    @SerialName(value = "code_point")
    val codePoint: String,

    val group: String,

    @ColumnInfo(name = "unicode_name")
    @SerialName(value = "unicode_name")
    val unicodeName: String,
)

fun EmojiEntity.asExternalModel(): Emoji {
    return Emoji(
        codePoint = codePoint,
        group = group,
        character = character,
        unicodeName = unicodeName,
    )
}
