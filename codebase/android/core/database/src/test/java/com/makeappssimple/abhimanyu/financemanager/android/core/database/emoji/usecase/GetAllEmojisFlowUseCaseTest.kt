package com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.repository.EmojiRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class GetAllEmojisFlowUseCaseTest {
    private val emojiRepository: EmojiRepository = mock()
    private lateinit var getAllEmojisFlowUseCase: GetAllEmojisFlowUseCase

    @Before
    fun setUp() {
        getAllEmojisFlowUseCase = GetAllEmojisFlowUseCaseImpl(
            emojiRepository = emojiRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        getAllEmojisFlowUseCase()

        verify(
            mock = emojiRepository,
        ).getAllEmojisFlow()
    }
}
