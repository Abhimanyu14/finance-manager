package com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.data.util.getTestSources
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SourceRepositoryTest {
    private val sourceDao: SourceDao = mock()
    private val id: Int = 1
    private val sources: Array<Source> = getTestSources()
    private lateinit var sourceRepository: SourceRepository

    @Before
    fun setUp() {
        sourceRepository =
            SourceRepositoryImpl(
                sourceDao = sourceDao,
            )
    }

    @Test
    fun getAllSourcesFlow() {
        sourceRepository.getAllSourcesFlow()

        verify(
            mock = sourceDao,
        ).getAllSourcesFlow()
    }

    @Test
    fun getAllSources() = runTest {
        sourceRepository.getAllSources()

        verify(
            mock = sourceDao,
        ).getAllSources()
    }

    @Test
    fun getAllSourcesCount() = runTest {
        sourceRepository.getAllSourcesCount()

        verify(
            mock = sourceDao,
        ).getAllSourcesCount()
    }

    @Test
    fun getSource() = runTest {
        sourceRepository.getSource(
            id = id,
        )

        verify(
            mock = sourceDao,
        ).getSource(
            id = id,
        )
    }

    @Test
    fun getSources() = runTest {
        sourceRepository.getSources(
            ids = listOf(id),
        )

        verify(
            mock = sourceDao,
        ).getSources(
            ids = listOf(id),
        )
    }

    @Test
    fun insertSources() = runTest {
        sourceRepository.insertSources(
            *sources,
        )

        verify(
            mock = sourceDao,
        ).insertSources(
            *sources,
        )
    }

    @Test
    fun updateSources() = runTest {
        sourceRepository.updateSources(
            *sources,
        )

        verify(
            mock = sourceDao,
        ).updateSources(
            *sources,
        )
    }

    @Test
    fun deleteSource() = runTest {
        sourceRepository.deleteSource(
            id = id,
        )

        verify(
            mock = sourceDao,
        ).deleteSource(
            id = id,
        )
    }

    @Test
    fun deleteSources() = runTest {
        sourceRepository.deleteSources(
            *sources,
        )

        verify(
            mock = sourceDao,
        ).deleteSources(
            *sources,
        )
    }
}