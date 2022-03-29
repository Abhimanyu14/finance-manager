package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.BuildConfig
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.remote.EmojiApi
import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.models.emoji.EmojiLocalEntity
import kotlinx.coroutines.flow.Flow

class EmojiRepositoryImpl(
    private val emojiDao: EmojiDao,
) : EmojiRepository {
    override val emojis: Flow<List<Emoji>> = emojiDao.getEmojis()
    //            .map {
    //                it.ifEmpty {
    //                    getEmojisDataFromRemote()
    //                }
    //            }

    init {
//        runBlocking(
//            context = Dispatchers.IO,
//        ) {
//            emojis.collect {
//                if (it.isEmpty()) {
//                    saveEmojisToDatabase()
//                }
//            }
//        }
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
}
