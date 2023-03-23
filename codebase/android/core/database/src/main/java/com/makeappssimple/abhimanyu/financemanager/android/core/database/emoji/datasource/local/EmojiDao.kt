package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmojiDao {

    @Query(value = "SELECT * from emoji_table")
    fun getAllEmojisFlow(): Flow<List<EmojiLocalEntity>>

    @Query(value = "SELECT * from emoji_table")
    suspend fun getAllEmojis(): List<EmojiLocalEntity>

    @Query(value = "SELECT COUNT(*) FROM emoji_table")
    suspend fun getAllEmojisCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmoji(
        emoji: EmojiLocalEntity,
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmojis(
        vararg emojis: EmojiLocalEntity,
    )

    @Query(value = "DELETE FROM emoji_table")
    suspend fun deleteAllEmojis()
}
