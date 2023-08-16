package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import androidx.emoji2.text.EmojiCompat
import com.makeappssimple.abhimanyu.financemanager.android.core.data.model.asEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import emoji.core.datasource.EmojiDataSource

class EmojiRepositoryImpl(
    private val emojiDataSource: EmojiDataSource,
) : EmojiRepository {
    override suspend fun getAllEmojis(): List<Emoji> {
        return emojiDataSource.getAllEmojis().filter {
            EmojiCompat.isConfigured() &&
                    EmojiCompat.get().loadState == EmojiCompat.LOAD_STATE_SUCCEEDED &&
                    EmojiCompat.get().getEmojiMatch(
                        it.character,
                        Int.MAX_VALUE
                    ) == EmojiCompat.EMOJI_SUPPORTED
        }.map {
            it.asEmoji()
        }
    }
}
