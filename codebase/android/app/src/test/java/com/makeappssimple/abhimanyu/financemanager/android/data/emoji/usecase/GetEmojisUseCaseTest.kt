package com.makeappssimple.abhimanyu.financemanager.android.data.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.emoji.repository.EmojiRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetEmojisUseCaseTest {
    private val emojiRepository: EmojiRepository = mock()
    private lateinit var getEmojisUseCase: GetEmojisUseCase

    @Before
    fun setUp() {
        getEmojisUseCase = GetEmojisUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getEmojisUseCase()

        verify(
            mock = emojiRepository,
        ).emojis
    }
}
