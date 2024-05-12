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
import com.makeappssimple.abhimanyu.financemanager.android.core.model.ReminderConstant
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.seconds

@Ignore("Fix Hilt")
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
public class MyPreferencesDataSourceTest {
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testDispatcher + Job(),
    )

    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    public val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private lateinit var testDataStore: DataStore<Preferences>

    //    private lateinit var testDispatcherProvider: DispatcherProvider
    private lateinit var testMyLogger: MyLogger

    private lateinit var myPreferencesDataSource: MyPreferencesDataSource

    @Before
    public fun setUp() {
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
//        testDispatcherProvider = TestDispatcherProviderImpl(
//            testDispatcher = testDispatcher,
//        )
        myPreferencesDataSource = MyPreferencesDataSource(
            dataStore = testDataStore,
            myLogger = testMyLogger,
        )
    }

    @After
    public fun tearDown() {
        testScope.cancel()
    }

    @Test
    public fun getDataTimestampLastBackup_returnsZero(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertEquals(
            0L,
            result,
        )
    }

    @Test
    public fun getDataTimestampLastChange_returnsZero(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertEquals(
            0L,
            result,
        )
    }

    @Test
    public fun setLastDataBackupTimestamp_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setLastDataChangeTimestamp_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun getDefaultDataIdExpenseCategory_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun getDefaultDataIdIncomeCategory_returnsZero(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    public fun getDefaultDataIdInvestmentCategory_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun getDefaultDataIdAccount_returnsZero(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertEquals(
            0,
            result,
        )
    }

    @Test
    public fun setDefaultExpenseCategoryId_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setDefaultIncomeCategoryId_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setDefaultInvestmentCategoryId_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setDefaultAccountId_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun getInitialDataVersionNumberAccount_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.account

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun getInitialDataVersionNumberCategory_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.category

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun getInitialDataVersionNumberTransaction_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transaction

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun getInitialDataVersionNumberTransactionFor_returnsZero(): TestResult =
        runTestAndClearDataStore {
            val result =
                myPreferencesDataSource.getInitialDataVersionNumber().first()?.transactionFor

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    public fun setAccountDataVersionNumber_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setCategoryDataVersionNumber_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setTransactionDataVersionNumber_defaultTest(): TestResult =
        runTestAndClearDataStore {
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
    public fun setTransactionForDataVersionNumber_defaultTest(): TestResult =
        runTestAndClearDataStore {
            myPreferencesDataSource.setTransactionForDataVersionNumber(
                transactionForDataVersionNumber = TEST_ID,
            )

            val result =
                myPreferencesDataSource.getInitialDataVersionNumber().first()?.transactionFor

            Assert.assertEquals(
                TEST_ID,
                result,
            )
        }

    @Test
    public fun getReminderIsEnabled_returnsFalse(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.isEnabled

        Assert.assertFalse(result.orFalse())
    }

    @Test
    public fun getReminderHour_returnsDefaultValue(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.hour

        Assert.assertEquals(
            ReminderConstant.DEFAULT_REMINDER_HOUR,
            result,
        )
    }

    @Test
    public fun getReminderMin_returnsDefaultValue(): TestResult = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.min

        Assert.assertEquals(
            ReminderConstant.DEFAULT_REMINDER_MIN,
            result,
        )
    }

    @Test
    public fun setIsReminderEnabled_defaultTest(): TestResult = runTestAndClearDataStore {
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
    public fun setReminderTime_defaultTest(): TestResult = runTestAndClearDataStore {
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
    ) = testScope.runTest(
        timeout = 2.seconds,
    ) {
        block()
        testDataStore.edit {
            it.clear()
        }
    }

    public companion object {
        public const val TEST_ID: Int = 34
        public const val TEST_TIMESTAMP: Long = 32654L
    }
}
