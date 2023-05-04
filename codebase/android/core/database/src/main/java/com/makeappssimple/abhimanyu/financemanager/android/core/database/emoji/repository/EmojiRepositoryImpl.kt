package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

class EmojiRepositoryImpl(
    private val emojiDao: EmojiDao,
) : EmojiRepository {

    override fun getAllEmojisFlow(): Flow<List<EmojiLocalEntity>> {
        return emojiDao.getAllEmojisFlow()
    }

    override suspend fun getAllEmojis(): List<EmojiLocalEntity> {
        return emojiDao.getAllEmojis()
    }
}
