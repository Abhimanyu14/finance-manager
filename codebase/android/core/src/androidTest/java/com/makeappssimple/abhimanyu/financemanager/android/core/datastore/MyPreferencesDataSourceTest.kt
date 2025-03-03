package com.makeappssimple.abhimanyu.financemanager.android.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.constants.DatastoreConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.model.ReminderConstants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.seconds

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
internal class MyPreferencesDataSourceTest {
    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testDispatcher + Job(),
    )
    private lateinit var testDataStore: DataStore<Preferences>

    private lateinit var myPreferencesDataSource: MyPreferencesDataSource

    @Before
    fun setUp() {
        hiltRule.inject()

        testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope.backgroundScope,
            produceFile = {
                testContext.preferencesDataStoreFile(
                    name = DatastoreConstants.DATASTORE_FILE_NAME,
                )
            },
        )
        myPreferencesDataSource = MyPreferencesDataSource(
            dataStore = testDataStore,
            logKit = FakeLogKitImpl(),
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
    fun updateLastDataBackupTimestamp_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateLastDataBackupTimestamp(
            lastDataBackupTimestamp = TEST_TIMESTAMP,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastBackup

        Assert.assertEquals(
            TEST_TIMESTAMP,
            result,
        )
    }

    @Test
    fun updateLastDataChangeTimestamp_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateLastDataChangeTimestamp(
            lastDataChangeTimestamp = TEST_TIMESTAMP,
        )

        val result = myPreferencesDataSource.getDataTimestamp().first()?.lastChange

        Assert.assertEquals(
            TEST_TIMESTAMP,
            result,
        )
    }

    @Test
    fun getDefaultDataIdExpenseCategory_returnsZero() =
        runTestAndClearDataStore {
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
    fun getDefaultDataIdInvestmentCategory_returnsZero() =
        runTestAndClearDataStore {
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
    fun updateDefaultExpenseCategoryId_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateDefaultExpenseCategoryId(
            defaultExpenseCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.expenseCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun updateDefaultIncomeCategoryId_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateDefaultIncomeCategoryId(
            defaultIncomeCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.incomeCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun updateDefaultInvestmentCategoryId_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateDefaultInvestmentCategoryId(
            defaultInvestmentCategoryId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.investmentCategory

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun updateDefaultAccountId_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateDefaultAccountId(
            defaultAccountId = TEST_ID,
        )

        val result = myPreferencesDataSource.getDefaultDataId().first()?.account

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun getInitialDataVersionNumberAccount_returnsZero() =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.account

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    fun getInitialDataVersionNumberCategory_returnsZero() =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.category

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    fun getInitialDataVersionNumberTransaction_returnsZero() =
        runTestAndClearDataStore {
            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transaction

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    fun getInitialDataVersionNumberTransactionFor_returnsZero() =
        runTestAndClearDataStore {
            val result =
                myPreferencesDataSource.getInitialDataVersionNumber().first()?.transactionFor

            Assert.assertEquals(
                0,
                result,
            )
        }

    @Test
    fun updateAccountDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateAccountDataVersionNumber(
            accountDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.account

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun updateCategoryDataVersionNumber_defaultTest() = runTestAndClearDataStore {
        myPreferencesDataSource.updateCategoryDataVersionNumber(
            categoryDataVersionNumber = TEST_ID,
        )

        val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.category

        Assert.assertEquals(
            TEST_ID,
            result,
        )
    }

    @Test
    fun updateTransactionDataVersionNumber_defaultTest() =
        runTestAndClearDataStore {
            myPreferencesDataSource.updateTransactionDataVersionNumber(
                transactionDataVersionNumber = TEST_ID,
            )

            val result = myPreferencesDataSource.getInitialDataVersionNumber().first()?.transaction

            Assert.assertEquals(
                TEST_ID,
                result,
            )
        }

    @Test
    fun updateTransactionForDataVersionNumber_defaultTest() =
        runTestAndClearDataStore {
            myPreferencesDataSource.updateTransactionForDataVersionNumber(
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
    fun getReminderIsEnabled_returnsFalse() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.isEnabled

        Assert.assertFalse(result.orFalse())
    }

    @Test
    fun getReminderHour_returnsDefaultValue() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.hour

        Assert.assertEquals(
            ReminderConstants.DEFAULT_REMINDER_HOUR,
            result,
        )
    }

    @Test
    fun getReminderMin_returnsDefaultValue() = runTestAndClearDataStore {
        val result = myPreferencesDataSource.getReminder().first()?.min

        Assert.assertEquals(
            ReminderConstants.DEFAULT_REMINDER_MIN,
            result,
        )
    }

    @Test
    fun updateIsReminderEnabled_defaultTest() = runTestAndClearDataStore {
        val testValue = true
        myPreferencesDataSource.updateIsReminderEnabled(
            isReminderEnabled = testValue,
        )

        val result = myPreferencesDataSource.getReminder().first()?.isEnabled

        Assert.assertEquals(
            testValue,
            result,
        )
    }

    @Test
    fun updateReminderTime_defaultTest() = runTestAndClearDataStore {
        val testHourValue = 10
        val testMinValue = 12
        myPreferencesDataSource.updateReminderTime(
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
        block: suspend TestScope.() -> Unit,
    ) = testScope.runTest(
        timeout = 2.seconds,
    ) {
        block()
        testDataStore.edit {
            it.clear()
        }
    }

    companion object {
        const val TEST_ID: Int = 34
        const val TEST_TIMESTAMP: Long = 32654L
    }
}
