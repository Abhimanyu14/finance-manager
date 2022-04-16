package com.makeappssimple.abhimanyu.financemanager.android.data.source.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestSource
import com.makeappssimple.abhimanyu.financemanager.android.utils.getTestSources
import kotlinx.coroutines.runBlocking
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
    fun getSourcesCount() {
        runBlocking {
            sourceRepository.getSourcesCount()

            verify(
                mock = sourceDao,
            ).getSourcesCount()
        }
    }

    @Test
    fun getSource() {
        runBlocking {
            sourceRepository.getSource(
                id = id,
            )

            verify(
                mock = sourceDao,
            ).getSource(
                id = id,
            )
        }
    }

    @Test
    fun insertSource() {
        runBlocking {
            sourceRepository.insertSource(
                source = source,
            )

            verify(
                mock = sourceDao,
            ).insertSource(
                source = source,
            )
        }
    }

    @Test
    fun insertSources() {
        runBlocking {
            sourceRepository.insertSources(
                *sources,
            )

            verify(
                mock = sourceDao,
            ).insertSources(
                *sources,
            )
        }
    }

    @Test
    fun updateSources() {
        runBlocking {
            sourceRepository.updateSources(
                *sources,
            )

            verify(
                mock = sourceDao,
            ).updateSources(
                *sources,
            )
        }
    }

    @Test
    fun deleteSource() {
        runBlocking {
            sourceRepository.deleteSource(
                id = id,
            )

            verify(
                mock = sourceDao,
            ).deleteSource(
                id = id,
            )
        }
    }

    @Test
    fun deleteSources() {
        runBlocking {
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

    @Test
    fun deleteAllSources() {
        runBlocking {
            sourceRepository.deleteAllSources()

            verify(
                mock = sourceDao,
            ).deleteAllSources()
        }
    }
}
