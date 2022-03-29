package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

interface EmojiRepository {
    val emojis: Flow<List<Emoji>>

    suspend fun getEmoji(
        character: String,
    ): Emoji?

    suspend fun insertEmoji(
        emoji: EmojiLocalEntity,
    )

    suspend fun deleteEmoji(
        character: String,
    )

    suspend fun deleteEmojis(
        vararg emojis: EmojiLocalEntity,
    )
}
