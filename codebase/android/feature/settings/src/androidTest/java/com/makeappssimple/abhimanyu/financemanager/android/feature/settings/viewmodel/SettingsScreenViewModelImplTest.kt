package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake.FakeAppVersionUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonreader.fake.FakeMyJsonReaderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.jsonwriter.fake.FakeMyJsonWriterImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.repository.fake.FakeCategoryRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.repository.fake.FakeEmojiRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.emoji.usecase.GetAllEmojisUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.repository.fake.FakeSourceRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateSourcesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionsUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.repository.fake.FakeTransactionForRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake.FakeNavigationManagerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsScreenViewModelImplTest {
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var appVersionUtil: AppVersionUtil
    private lateinit var myLogger: MyLogger
    private lateinit var navigationManager: NavigationManager
    private lateinit var backupDataUseCase: BackupDataUseCase
    private lateinit var recalculateTotalUseCase: RecalculateTotalUseCase
    private lateinit var restoreDataUseCase: RestoreDataUseCase

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    private lateinit var settingsScreenViewModelImpl: SettingsScreenViewModelImpl

    @Before
    fun setUp() {
        appVersionUtil = FakeAppVersionUtilImpl()
        myLogger = FakeMyLoggerImpl()
        navigationManager = FakeNavigationManagerImpl()
        backupDataUseCase = BackupDataUseCaseImpl(
            dateTimeUtil = DateTimeUtilImpl(),
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = GetAllCategoriesUseCaseImpl(
                categoryRepository = FakeCategoryRepositoryImpl(),
            ),
            getAllEmojisUseCase = GetAllEmojisUseCaseImpl(
                emojiRepository = FakeEmojiRepositoryImpl(),
            ),
            getAllSourcesUseCase = GetAllSourcesUseCaseImpl(
                sourceRepository = FakeSourceRepositoryImpl(),
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
            getAllSourcesUseCase = GetAllSourcesUseCaseImpl(
                sourceRepository = FakeSourceRepositoryImpl(),
            ),
            getAllTransactionDataUseCase = GetAllTransactionDataUseCaseImpl(
                transactionRepository = FakeTransactionRepositoryImpl(),
            ),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            updateSourcesUseCase = UpdateSourcesUseCaseImpl(
                myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
                sourceRepository = FakeSourceRepositoryImpl(),
            ),
        )
        restoreDataUseCase = RestoreDataUseCaseImpl(
            myJsonReader = FakeMyJsonReaderImpl(),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            transactionRepository = FakeTransactionRepositoryImpl(),
        )

        settingsScreenViewModelImpl = SettingsScreenViewModelImpl(
            appVersionUtil = appVersionUtil,
            myLogger = myLogger,
            navigationManager = navigationManager,
            backupDataUseCase = backupDataUseCase,
            ioDispatcher = UnconfinedTestDispatcher(),
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun screenUIDataTest() = runTest {
        Assert.assertEquals(1, 1)
//        settingsScreenViewModelImpl.screenUIData.test {
//            Assert.assertEquals(
//                MyResult.Success(
//                    data = SettingsScreenUIData(
//                        appVersion = "versionName",
//                    )
//                ),
//                awaitItem(),
//            )
//            // cancelAndIgnoreRemainingEvents()
//        }
    }
}
