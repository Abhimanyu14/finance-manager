package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

interface EmojiRepository {
    val emojis: Flow<List<EmojiLocalEntity>>

    suspend fun getEmojisCount(): Int

    suspend fun getEmoji(
        character: String,
    ): EmojiLocalEntity?

    suspend fun insertEmoji(
        emoji: EmojiLocalEntity,
    )

    suspend fun insertEmojis(
        vararg emojis: EmojiLocalEntity,
    )

    suspend fun deleteEmoji(
        character: String,
    )

    suspend fun deleteEmojis(
        vararg emojis: EmojiLocalEntity,
    )

    suspend fun deleteAllEmojis()
}
