package com.makeappssimple.abhimanyu.financemanager.android.core.database.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestSource
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.getTestSources
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SourceRepositoryImplTest {
    private val sourceDao: SourceDao = mock()
    private val id: Int = 1
    private val source: Source = getTestSource()
    private val sources: Array<Source> = getTestSources()
    private lateinit var sourceRepository: SourceRepository

    @Before
    fun setUp() {
        sourceRepository = SourceRepositoryImpl(
            sourceDao = sourceDao,
        )
    }

    @Test
    fun getSources() {
        sourceRepository.sources

        verify(
            mock = sourceDao,
        ).getSources()
    }

    @Test
    fun getSourcesCount() = runTest {
        sourceRepository.getSourcesCount()

        verify(
            mock = sourceDao,
        ).getSourcesCount()
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
    fun insertSource() = runTest {
        sourceRepository.insertSource(
            source = source,
        )

        verify(
            mock = sourceDao,
        ).insertSource(
            source = source,
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

    @Test
    fun deleteAllSources() = runTest {
        sourceRepository.deleteAllSources()

        verify(
            mock = sourceDao,
        ).deleteAllSources()
    }
}
