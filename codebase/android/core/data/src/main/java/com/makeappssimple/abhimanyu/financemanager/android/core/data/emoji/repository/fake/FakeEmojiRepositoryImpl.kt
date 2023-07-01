package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji

class FakeEmojiRepositoryImpl : EmojiRepository {
    override suspend fun getAllEmojis(): List<Emoji> {
        return emptyList()
//        return emojiDao.getAllEmojis().map {
//            it.asExternalModel()
//        }
    }
}
