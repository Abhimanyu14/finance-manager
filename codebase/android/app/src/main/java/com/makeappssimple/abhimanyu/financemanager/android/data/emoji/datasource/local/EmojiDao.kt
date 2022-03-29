package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmojiDao {

    @Query(
        value = "SELECT * from emoji_table",
    )
    fun getEmojis(): Flow<List<EmojiLocalEntity>>

    @Query(
        value = "SELECT * from emoji_table WHERE character = :character",
    )
    suspend fun getEmoji(
        character: String,
    ): EmojiLocalEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmoji(
        emoji: EmojiLocalEntity,
    )

    @Query(
        value = "DELETE FROM emoji_table WHERE character = :character",
    )
    suspend fun deleteEmoji(
        character: String,
    )

    @Delete
    suspend fun deleteEmojis(
        vararg emojis: EmojiLocalEntity,
    )

    @Query(
        value = "DELETE FROM emoji_table",
    )
    suspend fun deleteAllEmojis()
}
