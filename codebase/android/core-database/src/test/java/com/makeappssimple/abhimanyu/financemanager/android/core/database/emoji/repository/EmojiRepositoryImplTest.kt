package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.datasource.local.EmojiDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestEmoji
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestEmojis
import kotlinx.coroutines.test.runTest
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
    fun getEmojisCount() = runTest {
        emojiRepository.getEmojisCount()

        verify(
            mock = emojiDao,
        ).getEmojisCount()
    }

    @Test
    fun getEmoji() = runTest {
        emojiRepository.getEmoji(
            character = character,
        )

        verify(
            mock = emojiDao,
        ).getEmoji(
            character = character,
        )
    }

    @Test
    fun insertEmoji() = runTest {
        emojiRepository.insertEmoji(
            emoji = emoji,
        )

        verify(
            mock = emojiDao,
        ).insertEmoji(
            emoji = emoji,
        )
    }

    @Test
    fun insertEmojis() = runTest {
        emojiRepository.insertEmojis(
            *emojis,
        )

        verify(
            mock = emojiDao,
        ).insertEmojis(
            *emojis,
        )
    }

    @Test
    fun deleteEmoji() = runTest {
        emojiRepository.deleteEmoji(
            character = character,
        )

        verify(
            mock = emojiDao,
        ).deleteEmoji(
            character = character,
        )
    }

    @Test
    fun deleteEmojis() = runTest {
        emojiRepository.deleteEmojis(
            *emojis,
        )

        verify(
            mock = emojiDao,
        ).deleteEmojis(
            *emojis,
        )
    }

    @Test
    fun deleteAllEmojis() = runTest {
        emojiRepository.deleteAllEmojis()

        verify(
            mock = emojiDao,
        ).deleteAllEmojis()
    }
}
