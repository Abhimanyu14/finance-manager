package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

interface EmojiRepository {
    fun getAllEmojisFlow(): Flow<List<EmojiLocalEntity>>

    suspend fun getAllEmojis(): List<EmojiLocalEntity>
}
