package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MyPreferencesDataSourceTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var testMyLogger: MyLogger

    private lateinit var myPreferencesDataSource: MyPreferencesDataSource

    @Before
    fun setUp() {
        hiltRule.inject()

        testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = {
                testContext.preferencesDataStoreFile(
                    name = AppConstants.APP_NAME,
                )
            },
        )
        testMyLogger = FakeMyLoggerImpl()
        myPreferencesDataSource = MyPreferencesDataSource(
            dataStore = testDataStore,
            myLogger = testMyLogger,
        )
    }

    @After
    fun tearDown() {
        testScope.runTest {
            testDataStore.edit {
                it.clear()
            }
        }
        testScope.cancel()
    }

    @Test
    fun getDefaultExpenseCategoryIdFromDataStore_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultExpenseCategoryIdInDataStore_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setDefaultExpenseCategoryId(
            defaultExpenseCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getDefaultIncomeCategoryIdFromDataStore_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultIncomeCategoryIdInDataStore_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setDefaultIncomeCategoryId(
            defaultIncomeCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getDefaultInvestmentCategoryIdFromDataStore_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultInvestmentCategoryIdInDataStore_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setDefaultInvestmentCategoryId(
            defaultInvestmentCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getDefaultAccountIdFromDataStore_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultAccountIdInDataStore_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setDefaultAccountId(
            defaultAccountId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getLastDataBackupTimestamp_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataBackupTimestamp_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setLastDataBackupTimestamp(
            lastDataBackupTimestamp = TEST_TIMESTAMP,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertEquals(
            TEST_TIMESTAMP,
            result,
        )
    }

    @Test
    fun getLastDataChangeTimestamp_returnsNull() = testScope.runTest {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataChangeTimestamp_defaultTest() = testScope.runTest {
        myPreferencesDataSource.setLastDataChangeTimestamp(
            lastDataChangeTimestamp = TEST_TIMESTAMP,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertEquals(
            TEST_TIMESTAMP,
            result,
        )
    }

    companion object {
        const val TEST_ID = 34
        const val TEST_TIMESTAMP = 32654L
    }
}
