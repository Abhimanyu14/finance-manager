package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.alarm.AlarmKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersion
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionKit
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.common.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.time.Duration.Companion.seconds

@Ignore("Fix coroutines issues")
internal class SettingsScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(
        context = testDispatcher + Job(),
    )

    private lateinit var alarmKit: AlarmKit
    private lateinit var appVersionKit: AppVersionKit
    private lateinit var backupDataUseCase: BackupDataUseCase
    private lateinit var myPreferencesRepository: MyPreferencesRepository
    private lateinit var navigationKit: NavigationKit
    private lateinit var recalculateTotalUseCase: RecalculateTotalUseCase
    private lateinit var restoreDataUseCase: RestoreDataUseCase

    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    fun setUp() {
        alarmKit = mock()
        appVersionKit = mock()
        backupDataUseCase = mock()
        myPreferencesRepository = mock()
        navigationKit = mock()
        recalculateTotalUseCase = mock()
        restoreDataUseCase = mock()

        whenever(
            methodCall = myPreferencesRepository.getReminderFlow(),
        ).thenReturn(flow { })
        whenever(
            methodCall = appVersionKit.getAppVersion(),
        ).thenReturn(TEST_APP_VERSION)

        settingsScreenViewModel = SettingsScreenViewModel(
            coroutineScope = testScope,
            alarmKit = alarmKit,
            appVersionKit = appVersionKit,
            backupDataUseCase = backupDataUseCase,
            myPreferencesRepository = myPreferencesRepository,
            navigationKit = navigationKit,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }

    @After
    internal fun tearDown() {
    }

    @Test
    fun initViewModel_navigateToAccountsScreen() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiStateEvents = settingsScreenViewModel.uiStateEvents

        uiStateEvents.navigateToAccountsScreen()
        verify(
            mock = navigationKit,
        ).navigateToAccountsScreen()
    }

    @Test
    fun initViewModel_navigateToCategoriesScreen() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiStateEvents = settingsScreenViewModel.uiStateEvents

        uiStateEvents.navigateToCategoriesScreen()
        verify(
            mock = navigationKit,
        ).navigateToCategoriesScreen()
    }

    @Test
    fun initViewModel_navigateToOpenSourceLicensesScreen() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiStateEvents = settingsScreenViewModel.uiStateEvents

        uiStateEvents.navigateToOpenSourceLicensesScreen()
        verify(
            mock = navigationKit,
        ).navigateToOpenSourceLicensesScreen()
    }

    @Test
    fun initViewModel_navigateToTransactionForValuesScreen() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiStateEvents = settingsScreenViewModel.uiStateEvents

        uiStateEvents.navigateToTransactionForValuesScreen()
        verify(
            mock = navigationKit,
        ).navigateToTransactionForValuesScreen()
    }

    @Test
    fun initViewModel_navigateUp() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiStateEvents = settingsScreenViewModel.uiStateEvents

        uiStateEvents.navigateUp()
        verify(
            mock = navigationKit,
        ).navigateUp()
    }

    @Test
    fun initViewModel_appVersion() = runTestWithTimeout {
        settingsScreenViewModel.initViewModel()

        val uiState = settingsScreenViewModel.uiState.first()

        Assert.assertEquals(
            TEST_APP_VERSION_NAME,
            uiState.appVersion,
        )
    }

    @Test
    @Ignore("Fix later")
    fun backupDataToDocument_backUpIsSuccessful() = runTestWithTimeout {
        val testUri = Uri.parse("")

        whenever(
            methodCall = backupDataUseCase(
                uri = testUri,
            ),
        ).thenReturn(true)

        settingsScreenViewModel.initViewModel()
        val uiStateEvents = settingsScreenViewModel.uiState
//        uiStateEvents.test {
//            val state1 = awaitItem()
//            Assert.assertEquals(
//                false,
//                state1.state.isLoading,
//            )
//
//            settingsScreenViewModel.backupDataToDocument(
//                uri = testUri,
//            )
//            val state2 = awaitItem()
//            Assert.assertEquals(
//                true,
//                state2.state.isLoading,
//            )
//
//            verify(
//                mock = navigator,
//            ).navigateUp()
//        }

//        turbineScope {
//            settingsScreenViewModel.backupDataToDocument(
//                uri = testUri,
//            )
//
//            val receiver = settingsScreenViewModel.navigator.command.testIn(
//                scope = backgroundScope,
//            )
//
//            Assert.assertEquals(
//                MyNavigationDirections.NavigateUp,
//                receiver.awaitItem(),
//            )
//            receiver.cancel()
//        }
    }

    private fun runTestWithTimeout(
        block: suspend TestScope.() -> Unit,
    ) = testScope.runTest(
        timeout = 3.seconds,
        testBody = block,
    )

    companion object {
        private const val TEST_APP_VERSION_NAME = "2024.07.27.0"
        private val TEST_APP_VERSION = AppVersion(
            versionName = TEST_APP_VERSION_NAME,
            versionNumber = 1L,
        )
    }
}

/*
    @Test fun navigateToCategoriesScreenTest() = runTest {
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

    @Test fun navigateToAccountsScreenTest() = runTest {
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

    @Test fun navigateToTransactionForValuesScreenTest() = runTest {
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

    @Test fun navigateUpTest() = runTest {
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

    @Test fun restoreDataFromDocumentTest() = runTest {
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

    @Test fun recalculateTotalTest() = runTest {
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
}
*/
