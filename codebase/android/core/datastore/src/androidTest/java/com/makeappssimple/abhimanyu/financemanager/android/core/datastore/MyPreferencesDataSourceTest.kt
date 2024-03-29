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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DEFAULT_REMINDER_HOUR
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DEFAULT_REMINDER_MIN
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
        testScope.cancel()
    }

    @Test
    fun getDataTimestampLastBackup_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertEquals(
            0L,
            result,
        )
    }

    @Test
    fun getDataTimestampLastChange_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertEquals(
            0L,
            result,
        )
    }

    @Test
    fun setLastDataBackupTimestamp_defaultTest() = runTestAndClearDataStore {
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
    fun setLastDataChangeTimestamp_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.setLastDataChangeTimestamp(
            lastDataChangeTimestamp = TEST_TIMESTAMP,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertEquals(
            TEST_TIMESTAMP,
            result,
        )
    }

    @Test
    fun getDefaultDataIdExpenseCategory_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getDefaultDataIdIncomeCategory_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getDefaultDataIdInvestmentCategory_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getDefaultDataIdAccount_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun setDefaultExpenseCategoryId_defaultTest() = runTestAndClearDataStore {
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
    fun setDefaultIncomeCategoryId_defaultTest() = runTestAndClearDataStore {
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
    fun setDefaultInvestmentCategoryId_defaultTest() = runTestAndClearDataStore {
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
    fun setDefaultAccountId_defaultTest() = runTestAndClearDataStore {
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
    fun getInitialDataVersionNumberAccount_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.account

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getInitialDataVersionNumberCategory_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.category

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getInitialDataVersionNumberTransaction_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transaction

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun getInitialDataVersionNumberTransactionFor_returnsZero() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transactionFor

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    fun setAccountDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.setAccountDataVersionNumber(
            accountDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.account

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun setCategoryDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.setCategoryDataVersionNumber(
            categoryDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.category

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun setTransactionDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.setTransactionDataVersionNumber(
            transactionDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transaction

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun setTransactionForDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.setTransactionForDataVersionNumber(
            transactionForDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transactionFor

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getReminderIsEnabled_returnsFalse() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.isEnabled

        Assert.assertFalse(result.orFalse())
    }

    @Test
    fun getReminderHour_returnsDefaultValue() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.hour

        Assert.assertEquals(
            DEFAULT_REMINDER_HOUR,
            result,
        )
    }

    @Test
    fun getReminderMin_returnsDefaultValue() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.min

        Assert.assertEquals(
            DEFAULT_REMINDER_MIN,
            result,
        )
    }

    @Test
    fun setIsReminderEnabled_defaultTest() = runTestAndClearDataStore {
        val testValue = true
        myPreferencesDataSource.setIsReminderEnabled(
            isReminderEnabled = testValue,
        )

        val result = myPreferencesDataSource.getReminder().first()?.isEnabled

        Assert.assertEquals(
            testValue,
            result,
        )
    }

    @Test
    fun setReminderTime_defaultTest() = runTestAndClearDataStore {
        val testHourValue = 10
        val testMinValue = 12
        myPreferencesDataSource.setReminderTime(
            hour = testHourValue,
            min = testMinValue,
        )

        val result = myPreferencesDataSource.getReminder().first()

        Assert.assertEquals(
            testHourValue,
            result?.hour,
        )
        Assert.assertEquals(
            testMinValue,
            result?.min,
        )
    }

    private fun runTestAndClearDataStore(
        block: suspend () -> Unit,
    ) = testScope.runTest {
        block()
        testDataStore.edit {
            it.clear()
        }
    }

    companion object {
        const val TEST_ID = 34
        const val TEST_TIMESTAMP = 32654L
    }
}
