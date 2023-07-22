package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.AppConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
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
import javax.inject.Inject

@HiltAndroidTest
class MyPreferencesDataSourceTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val testCoroutineScope = TestScope(
        context = dispatcherProvider.io + Job(),
    )
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testCoroutineScope,
        produceFile = {
            testContext.preferencesDataStoreFile(
                name = AppConstants.APP_NAME,
            )
        },
    )

    @Inject
    lateinit var myLogger: MyLogger

    private lateinit var myPreferencesDataSource: MyPreferencesDataSource

    @Before
    fun setUp() {
        myPreferencesDataSource = MyPreferencesDataSource(
            dataStore = testDataStore,
            myLogger = myLogger,
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
    fun getDefaultExpenseCategoryIdFromDataStore_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultExpenseCategoryIdInDataStore_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setDefaultExpenseCategoryId(
            defaultExpenseCategoryId = testId,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultIncomeCategoryIdFromDataStore_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultIncomeCategoryIdInDataStore_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setDefaultIncomeCategoryId(
            defaultIncomeCategoryId = testId,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultInvestmentCategoryIdFromDataStore_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultInvestmentCategoryIdInDataStore_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setDefaultInvestmentCategoryId(
            defaultInvestmentCategoryId = testId,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getDefaultAccountIdFromDataStore_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertNull(result)
    }

    @Test
    fun setDefaultAccountIdInDataStore_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setDefaultAccountId(
            defaultAccountId = testId,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertEquals(
            testId,
            result,
        )
    }

    @Test
    fun getLastDataBackupTimestamp_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataBackupTimestamp_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setLastDataBackupTimestamp(
            lastDataBackupTimestamp = testTimestamp,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertEquals(
            testTimestamp,
            result,
        )
    }

    @Test
    fun getLastDataChangeTimestamp_returnsNull() = testCoroutineScope.runTest {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertNull(result)
    }

    @Test
    fun setLastDataChangeTimestamp_defaultTest() = testCoroutineScope.runTest {
        myPreferencesDataSource.setLastDataChangeTimestamp(
            lastDataChangeTimestamp = testTimestamp,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

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
