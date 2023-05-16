package com.makeappssimple.abhimanyu.financemanager.android.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class SourceDaoTest {
    private lateinit var myRoomDatabase: MyRoomDatabase
    private lateinit var sourceDao: SourceDao

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        myRoomDatabase = Room
            .inMemoryDatabaseBuilder(
                context,
                MyRoomDatabase::class.java,
            )
            .allowMainThreadQueries()
            .build()
        sourceDao = myRoomDatabase.sourceDao()
    }

    @After
    fun tearDown() {
        myRoomDatabase.close()
    }

    @Test
    fun getAllSources() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val result = sourceDao.getAllSources()

        // Note: Order of returned items will not match ordered of actual list
        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllSourcesFlow() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val result = sourceDao.getAllSourcesFlow().first()

        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun getAllSourcesCount() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val result = sourceDao.getAllSourcesCount()

        Assert.assertEquals(
            testSources.size,
            result,
        )
    }

    @Test
    fun getSource_returnsDataForValidId() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val result = sourceDao.getSource(
            id = testId2,
        )

        Assert.assertEquals(
            testSources[1],
            result,
        )
    }

    @Test
    fun getSource_returnsNullForInvalidId() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val result = sourceDao.getSource(
            id = invalidId,
        )

        Assert.assertNull(result)
    }

    @Test
    fun deleteSource_deleteDataOfGivenId() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        sourceDao.deleteSource(
            id = testId1,
        )
        val result = sourceDao.getAllSources()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            setOf(testSources[1], testSources[2]),
            result.toSet(),
        )
    }

    @Test
    fun deleteSource_noDeletionForInvalidId() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        sourceDao.deleteSource(
            id = invalidId,
        )
        val result = sourceDao.getAllSources()

        Assert.assertEquals(
            3,
            result.size,
        )
        Assert.assertEquals(
            testSources.toSet(),
            result.toSet(),
        )
    }

    @Test
    fun updateSources() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        val testSourceName2 = "IOB"
        sourceDao.updateSources(
            testSources[1].copy(
                name = testSourceName2,
            ),
            testSources[2].copy(
                id = 6342,
            ),
        )
        val result = sourceDao.getAllSources()

        Assert.assertEquals(
            3,
            result.size,
        )
        Assert.assertEquals(
            testSources[0],
            result[0],
        )
        Assert.assertEquals(
            testSources[2],
            result[2],
        )
        Assert.assertEquals(
            testSourceName2,
            result[1].name,
        )
    }

    @Test
    fun deleteSources() = runTest {
        sourceDao.insertSources(
            sources = testSources.toTypedArray(),
        )

        sourceDao.deleteSources(
            testSources[0].copy(
                name = "Random", // Data mismatch
            ),
            testSources[1].copy(
                id = invalidId,
            ),
        )
        val result = sourceDao.getAllSources()

        Assert.assertEquals(
            2,
            result.size,
        )
        Assert.assertEquals(
            setOf(testSources[1], testSources[2]),
            result.toSet(),
        )
    }

    companion object {
        private const val testId1 = 123
        private const val testId2 = 234
        private const val testId3 = 345
        private const val invalidId = 987
        private val testSources = listOf(
            SourceEntity(
                id = testId1,
                balanceAmount = AmountEntity(
                    value = testId1.toLong(),
                ),
                name = "Cash",
                type = SourceType.CASH,
            ),
            SourceEntity(
                id = testId2,
                balanceAmount = AmountEntity(
                    value = testId2.toLong(),
                ),
                name = "Axis",
                type = SourceType.BANK,
            ),
            SourceEntity(
                id = testId3,
                balanceAmount = AmountEntity(
                    value = testId3.toLong(),
                ),
                name = "Paytm",
                type = SourceType.E_WALLET,
            ),
        )
    }
}
