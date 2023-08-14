package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.emoji.core.remote.EmojiDataSource

class EmojiRepositoryImpl(
    private val emojiDataSource: EmojiDataSource,
) : EmojiRepository {
    override suspend fun getAllEmojis(): List<Emoji> {
        return emojiDataSource.getAllEmojis().map {
            it.asEmoji()
        }
    }
}
