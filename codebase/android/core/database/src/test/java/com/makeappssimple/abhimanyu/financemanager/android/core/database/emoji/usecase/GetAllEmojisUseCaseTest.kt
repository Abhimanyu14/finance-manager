package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllEmojisUseCaseTest {
    private val emojiRepository: EmojiRepository = mock()
    private lateinit var getAllEmojisUseCase: GetAllEmojisUseCase

    @Before
    fun setUp() {
        getAllEmojisUseCase = GetAllAllEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllEmojisUseCase()

        verify(
            mock = emojiRepository,
        ).getAllEmojisFlow()
    }
}
