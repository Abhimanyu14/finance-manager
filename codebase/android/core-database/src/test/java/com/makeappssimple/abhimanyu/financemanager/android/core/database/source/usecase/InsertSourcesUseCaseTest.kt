package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class InsertSourcesUseCaseTest {
    private val dataStore: MyDataStore = mock()
    private val sourceRepository: SourceRepository = mock()
    private lateinit var insertSourceUseCase: InsertSourcesUseCase

    @Before
    fun setUp() {
        insertSourceUseCase = InsertSourcesUseCaseImpl(
            dataStore = dataStore,
            sourceRepository = sourceRepository,
        )
    }

    @Test
    fun invoke_defaultTest() = runTest {
        val sources = getTestSources()
        insertSourceUseCase(
            *sources,
        )

        verify(
            mock = sourceRepository,
        ).insertSources(
            *sources,
        )
    }
}
