package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmojiDao {
    @Query(value = "SELECT * from emoji_table")
    fun getAllEmojisFlow(): Flow<List<EmojiEntity>>

    @Query(value = "SELECT * from emoji_table")
    suspend fun getAllEmojis(): List<EmojiEntity>

    @Query(value = "SELECT COUNT(*) FROM emoji_table")
    suspend fun getAllEmojisCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmoji(
        emoji: EmojiEntity,
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmojis(
        vararg emojis: EmojiEntity,
    )

    @Query(value = "DELETE FROM emoji_table")
    suspend fun deleteAllEmojis()
}
