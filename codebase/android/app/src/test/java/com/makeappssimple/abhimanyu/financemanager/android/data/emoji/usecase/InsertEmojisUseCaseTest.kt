package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestEmojis
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertEmojisUseCaseTest {
    private val emojiRepository: EmojiRepository = mock()
    private lateinit var insertEmojisUseCase: InsertEmojisUseCase

    @Before
    fun setUp() {
        insertEmojisUseCase = InsertEmojisUseCase(
            emojiRepository = emojiRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val emojis = getTestEmojis()
        runBlocking {
            insertEmojisUseCase(
                *emojis,
            )

            verify(
                mock = emojiRepository,
            ).insertEmojis(
                *emojis,
            )
        }
    }
}
