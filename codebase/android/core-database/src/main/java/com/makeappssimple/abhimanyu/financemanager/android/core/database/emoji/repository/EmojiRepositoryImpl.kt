package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.BuildConfig
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.remote.EmojiApi
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

class EmojiRepositoryImpl(
    private val emojiDao: EmojiDao,
) : EmojiRepository {
    override val emojis: Flow<List<EmojiLocalEntity>> = emojiDao.getEmojis()

    override suspend fun getEmojisCount(): Int {
        return emojiDao.getEmojisCount()
    }

    override suspend fun getEmoji(
        character: String,
    ): EmojiLocalEntity? {
        return emojiDao.getEmoji(
            character = character,
        )
    }

    override suspend fun insertEmoji(
        emoji: EmojiLocalEntity,
    ) {
        emojiDao.insertEmoji(
            emoji = emoji,
        )
    }

    override suspend fun insertEmojis(
        vararg emojis: EmojiLocalEntity,
    ) {
        emojiDao.insertEmojis(
            emojis = emojis,
        )
    }

    override suspend fun deleteEmoji(
        character: String,
    ) {
        emojiDao.deleteEmoji(
            character = character,
        )
    }

    override suspend fun deleteEmojis(
        vararg emojis: EmojiLocalEntity,
    ) {
        emojiDao.deleteEmojis(
            emojis = emojis,
        )
    }

    private suspend fun getEmojisDataFromRemote(): List<Emoji> {
        return EmojiApi
            .retrofitService
            .getEmojis(
                accessKey = BuildConfig.OPEN_EMOJI_KEY,
            )
            .distinctBy { emoji ->
                emoji.codePoint
            }
            .filter { emoji ->
                !emoji.unicodeName.contains("skin tone")
            }
    }

    private suspend fun saveEmojisToDatabase() {
        getEmojisDataFromRemote().forEach { emoji ->
            emojiDao.insertEmoji(
                emoji = EmojiLocalEntity(
                    emoji = emoji,
                ),
            )
        }
    }

    override suspend fun deleteAllEmojis() {
        emojiDao.deleteAllEmojis()
    }
}
