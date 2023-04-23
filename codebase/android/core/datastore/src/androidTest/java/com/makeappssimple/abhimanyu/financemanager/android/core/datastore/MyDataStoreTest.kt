package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.appName
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MyDataStoreTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )

    private val testCoroutineScope = TestScope(
        context = dispatcherProvider.io + Job(),
    )
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = {
            testContext.preferencesDataStoreFile(
                name = appName,
            )
        },
    )
    private val logger: Logger = LoggerImpl()
    private lateinit var myDataStore: MyDataStore

    @Before
    fun setUp() {
        myDataStore = MyDataStoreImpl(
            dataStore = testDataStore,
            logger = logger,
        )
    }

    @After
    fun tearDown() {
        testCoroutineScope.runTest {
            testDataStore.edit {
                it.clear()
            }
        }
        testCoroutineScope.cancel()
    }

    @Test
    fun getDefaultExpenseCategoryIdFromDataStore_returnsNull() = runTest {
        val result = myDataStore.getDefaultExpenseCategoryIdFromDataStore().first()

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultExpenseCategoryIdInDataStore_defaultTest() = runTest {
        myDataStore.setDefaultExpenseCategoryIdInDataStore(
            defaultExpenseCategoryId = testId,
        )

        val result = myDataStore.getDefaultExpenseCategoryIdFromDataStore().first()

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultIncomeCategoryIdFromDataStore_returnsNull() = runTest {
        val result = myDataStore.getDefaultIncomeCategoryIdFromDataStore().first()

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultIncomeCategoryIdInDataStore_defaultTest() = runTest {
        myDataStore.setDefaultIncomeCategoryIdInDataStore(
            defaultIncomeCategoryId = testId,
        )

        val result = myDataStore.getDefaultIncomeCategoryIdFromDataStore().first()

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultInvestmentCategoryIdFromDataStore_returnsNull() = runTest {
        val result = myDataStore.getDefaultInvestmentCategoryIdFromDataStore().first()

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultInvestmentCategoryIdInDataStore_defaultTest() = runTest {
        myDataStore.setDefaultInvestmentCategoryIdInDataStore(
            defaultInvestmentCategoryId = testId,
        )

        val result = myDataStore.getDefaultInvestmentCategoryIdFromDataStore().first()

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultSourceIdFromDataStore_returnsNull() = runTest {
        val result = myDataStore.getDefaultSourceIdFromDataStore().first()

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultSourceIdInDataStore_defaultTest() = runTest {
        myDataStore.setDefaultSourceIdInDataStore(
            defaultSourceId = testId,
        )

        val result = myDataStore.getDefaultSourceIdFromDataStore().first()

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getLastDataBackupTimestamp_returnsNull() = runTest {
        val result = myDataStore.getLastDataBackupTimestamp().first()

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataBackupTimestamp_defaultTest() = runTest {
        myDataStore.setLastDataBackupTimestamp(
            lastDataBackupTimestamp = testTimestamp,
        )

        val result = myDataStore.getLastDataBackupTimestamp().first()

        Assert.assertEquals(
            testTimestamp,
            result,
        )
    }

    @Test
    fun getLastDataChangeTimestamp_returnsNull() = runTest {
        val result = myDataStore.getLastDataChangeTimestamp().first()

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataChangeTimestamp_defaultTest() = runTest {
        myDataStore.setLastDataChangeTimestamp(
            lastDataChangeTimestamp = testTimestamp,
        )

        val result = myDataStore.getLastDataChangeTimestamp().first()

        Assert.assertEquals(
            testTimestamp,
            result,
        )
    }

    companion object {
        const val testId = 34
        const val testTimestamp = 32654L
    }
}
