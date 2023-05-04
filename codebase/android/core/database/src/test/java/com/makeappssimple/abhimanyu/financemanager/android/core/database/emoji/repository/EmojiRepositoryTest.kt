package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class EmojiRepositoryTest {
    private val emojiDao: EmojiDao = mock()
    private lateinit var emojiRepository: EmojiRepository

    @Before
    fun setUp() {
        emojiRepository = EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }

    @Test
    fun getAllEmojisFlow() {
        emojiRepository.getAllEmojisFlow()

        verify(
            mock = emojiDao,
        ).getAllEmojisFlow()
    }
}
