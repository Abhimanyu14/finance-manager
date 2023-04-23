package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SettingsScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val logger: Logger = LoggerImpl()
    private val navigationManager: NavigationManager = mock()
    private val backupDataUseCase: BackupDataUseCase = mock()
    private val recalculateTotalUseCase: RecalculateTotalUseCase = mock()
    private val restoreDataUseCase: RestoreDataUseCase = mock()
    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )

    @Before
    fun setUp() {
        settingsScreenViewModel = SettingsScreenViewModelImpl(
            logger = logger,
            navigationManager = navigationManager,
            backupDataUseCase = backupDataUseCase,
            dispatcherProvider = dispatcherProvider,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun backupDataToDocument() = runTest {
        val uri: Uri = mock()

        settingsScreenViewModel.backupDataToDocument(
            uri = uri,
        )

        verify(
            mock = backupDataUseCase,
        ).invoke(
            uri = uri,
        )
    }

    @Test
    fun restoreDataFromDocument() = runTest {
        val uri: Uri = mock()

        settingsScreenViewModel.restoreDataFromDocument(
            uri = uri,
        )

        verify(
            mock = restoreDataUseCase,
        ).invoke(
            uri = uri,
        )
    }

    @Test
    fun recalculateTotal() = runTest {
        settingsScreenViewModel.recalculateTotal()

        verify(
            mock = recalculateTotalUseCase,
        ).invoke()
    }
}
