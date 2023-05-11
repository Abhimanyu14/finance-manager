package com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.EmojiDao
import org.junit.Before
import org.mockito.kotlin.mock

class EmojiRepositoryTest {
    private val emojiDao: EmojiDao = mock()
    private lateinit var emojiRepository: EmojiRepository

    @Before
    fun setUp() {
        emojiRepository = EmojiRepositoryImpl(
            emojiDao = emojiDao,
        )
    }
// TODO(Abhi): Change to test without mocking
//    @Test
//    fun getAllEmojis() = runTest {
//        emojiRepository.getAllEmojis()
//
//        verify(
//            mock = emojiDao,
//        ).getAllEmojis()
//    }
}
