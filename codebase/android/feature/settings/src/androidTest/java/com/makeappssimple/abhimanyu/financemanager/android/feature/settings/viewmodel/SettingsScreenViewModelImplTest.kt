package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.alarmkit.fake.FakeAlarmKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.MyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.fake.FakeMyJsonWriterImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.account.fake.FakeAccountRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.category.fake.FakeCategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.fake.FakeTransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake.FakeNavigatorImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.appversion.TestAppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.jsonreader.TestMyJsonReader
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.logger.TestMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.repository.TestMyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModelImpl
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsScreenViewModelImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var appVersionUtil: AppVersionUtil
    private lateinit var myLogger: MyLogger
    private lateinit var navigator: Navigator
    private lateinit var backupDataUseCase: BackupDataUseCase
    private lateinit var recalculateTotalUseCase: RecalculateTotalUseCase
    private lateinit var restoreDataUseCase: RestoreDataUseCase
    private lateinit var myJsonReader: MyJsonReader
    private lateinit var myPreferencesRepository: MyPreferencesRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var alarmKit: AlarmKit

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    private lateinit var settingsScreenViewModelImpl: SettingsScreenViewModelImpl

    @Before
    fun setUp() {
        appVersionUtil = TestAppVersionUtil()
        myLogger = TestMyLogger()
        myJsonReader = TestMyJsonReader()
        myPreferencesRepository = TestMyPreferencesRepository()
        transactionRepository = FakeTransactionRepositoryImpl()
        alarmKit = FakeAlarmKitImpl()
        backupDataUseCase = BackupDataUseCaseImpl(
            dateTimeUtil = DateTimeUtilImpl(),
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = GetAllCategoriesUseCaseImpl(
                categoryRepository = FakeCategoryRepositoryImpl(),
            ),
            getAllAccountsUseCase = GetAllAccountsUseCaseImpl(
                accountRepository = FakeAccountRepositoryImpl(),
            ),
            getAllTransactionForValuesUseCase = GetAllTransactionForValuesUseCaseImpl(
                transactionForRepository = FakeTransactionForRepositoryImpl(),
            ),
            getAllTransactionsUseCase = GetAllTransactionsUseCaseImpl(
                transactionRepository = FakeTransactionRepositoryImpl(),
            ),
            myJsonWriter = FakeMyJsonWriterImpl(),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
        )
        recalculateTotalUseCase = RecalculateTotalUseCaseImpl(
            dispatcherProvider = dispatcherProvider,
            getAllAccountsUseCase = GetAllAccountsUseCaseImpl(
                accountRepository = FakeAccountRepositoryImpl(),
            ),
            getAllTransactionDataUseCase = GetAllTransactionDataUseCaseImpl(
                transactionRepository = FakeTransactionRepositoryImpl(),
            ),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            updateAccountsUseCase = UpdateAccountsUseCaseImpl(
                myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
                accountRepository = FakeAccountRepositoryImpl(),
            ),
        )
        restoreDataUseCase = RestoreDataUseCaseImpl(
            myJsonReader = myJsonReader,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun backupDataToDocumentTest() = runTest {
        val testUri = Uri.EMPTY
        navigator = FakeNavigatorImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            settingsScreenViewModelImpl.backupDataToDocument(
                uri = testUri,
            )

            val receiver = settingsScreenViewModelImpl.navigator.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    /*
    @Test
    fun navigateToCategoriesScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToCategoriesScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.Categories,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    fun navigateToAccountsScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToAccountsScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.Accounts,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    fun navigateToTransactionForValuesScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToTransactionForValuesScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.TransactionForValues,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    fun navigateUpTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateUp()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    fun restoreDataFromDocumentTest() = runTest {
        val testUri = Uri.EMPTY
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            settingsScreenViewModelImpl.restoreDataFromDocument(
                uri = testUri,
            )

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    fun recalculateTotalTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            settingsScreenViewModelImpl.recalculateTotal()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }
    */

    private fun initViewModel() {
        settingsScreenViewModelImpl = SettingsScreenViewModelImpl(
            appVersionUtil = appVersionUtil,
            myPreferencesRepository = myPreferencesRepository,
            alarmKit = alarmKit,
            backupDataUseCase = backupDataUseCase,
            ioDispatcher = dispatcherProvider.io,
            navigator = navigator,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }
}
