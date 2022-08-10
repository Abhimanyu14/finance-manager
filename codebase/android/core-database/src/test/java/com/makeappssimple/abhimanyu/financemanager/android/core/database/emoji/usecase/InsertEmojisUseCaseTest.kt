package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestEmojis
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertEmojisUseCaseTest {
    private val emojiRepository: EmojiRepository = mock()
    private lateinit var insertEmojisUseCase: InsertEmojisUseCase

    @Before
    fun setUp() {
        insertEmojisUseCase = InsertEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val emojis = getTestEmojis()
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
