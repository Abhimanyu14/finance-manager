package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.asExternalModel
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji

class EmojiRepositoryImpl(
    private val emojiDao: EmojiDao,
) : EmojiRepository {
    override suspend fun getAllEmojis(): List<Emoji> {
        return emojiDao.getAllEmojis().map {
            it.asExternalModel()
        }
    }
}
