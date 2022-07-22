package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertSourceUseCaseTest {
    private val sourceRepository: SourceRepository = mock()
    private lateinit var insertSourceUseCase: InsertSourceUseCase

    @Before
    fun setUp() {
        insertSourceUseCase = InsertSourceUseCaseImpl(
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val source = getTestSource()
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
