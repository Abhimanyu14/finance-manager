package com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestSource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertSourceUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var insertSourceUseCase: InsertSourceUseCase

    @Before
    fun setUp() {
        insertSourceUseCase = InsertSourceUseCase(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() {
        val source = getTestSource()
        runBlocking {
            insertSourceUseCase(
                source = source,
            )

            verify(
                mock = sourceRepository,
            ).insertSource(
                source = source,
            )
        }
    }
}
