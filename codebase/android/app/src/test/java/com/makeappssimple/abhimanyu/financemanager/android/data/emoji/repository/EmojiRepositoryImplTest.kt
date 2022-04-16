package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestEmoji
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestEmojis
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class EmojiRepositoryImplTest {
    private val emojiDao: EmojiDao = mock()
    private val character: String = "😀"
    private val emoji = getTestEmoji()
    private val emojis = getTestEmojis()
    private lateinit var emojiRepository: EmojiRepository

    @Before
    fun setUp() {
        emojiRepository = EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }

    @Test
    fun getEmojis() {
        emojiRepository.emojis

        verify(
            mock = emojiDao,
        ).getEmojis()
    }

    @Test
    fun getEmojisCount() {
        runBlocking {
            emojiRepository.getEmojisCount()

            verify(
                mock = emojiDao,
            ).getEmojisCount()
        }
    }

    @Test
    fun getEmoji() {
        runBlocking {
            emojiRepository.getEmoji(
                character = character,
            )

            verify(
                mock = emojiDao,
            ).getEmoji(
                character = character,
            )
        }
    }

    @Test
    fun insertEmoji() {
        runBlocking {
            emojiRepository.insertEmoji(
                emoji = emoji,
            )

            verify(
                mock = emojiDao,
            ).insertEmoji(
                emoji = emoji,
            )
        }
    }

    @Test
    fun insertEmojis() {
        runBlocking {
            emojiRepository.insertEmojis(
                *emojis,
            )

            verify(
                mock = emojiDao,
            ).insertEmojis(
                *emojis,
            )
        }
    }

    @Test
    fun deleteEmoji() {
        runBlocking {
            emojiRepository.deleteEmoji(
                character = character,
            )

            verify(
                mock = emojiDao,
            ).deleteEmoji(
                character = character,
            )
        }
    }

    @Test
    fun deleteEmojis() {
        runBlocking {
            emojiRepository.deleteEmojis(
                *emojis,
            )

            verify(
                mock = emojiDao,
            ).deleteEmojis(
                *emojis,
            )
        }
    }

    @Test
    fun deleteAllEmojis() {
        runBlocking {
            emojiRepository.deleteAllEmojis()

            verify(
                mock = emojiDao,
            ).deleteAllEmojis()
        }
    }
}
