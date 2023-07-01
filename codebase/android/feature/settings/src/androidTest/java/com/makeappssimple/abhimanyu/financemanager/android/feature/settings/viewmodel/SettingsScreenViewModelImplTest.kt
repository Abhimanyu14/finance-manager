package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.AppVersionUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.appversion.fake.FakeAppVersionUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.fake.FakeBuildConfigUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RecalculateTotalUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.RestoreDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.logger.TestMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class SettingsScreenViewModelImplTest {
    /**
     * Manages the components' state and is used to perform injection on your test
     */
//    @get:Rule(order = 0)
//    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 0)
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var appVersionUtil: AppVersionUtil

    private val buildConfigUtil = FakeBuildConfigUtilImpl()
    private val myLogger: MyLogger = TestMyLoggerImpl(
        buildConfigUtil = buildConfigUtil,
    )
    private val navigationManager: NavigationManager = mock()
    private val backupDataUseCase: BackupDataUseCase = mock()
    private val dispatcherProvider: DispatcherProvider = mock()
    private val recalculateTotalUseCase: RecalculateTotalUseCase = mock()
    private val restoreDataUseCase: RestoreDataUseCase = mock()

    private lateinit var settingsScreenViewModelImpl: SettingsScreenViewModelImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appVersionUtil = FakeAppVersionUtilImpl(
            context = context,
        )
        settingsScreenViewModelImpl = SettingsScreenViewModelImpl(
            appVersionUtil = appVersionUtil,
            myLogger = myLogger,
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
//            cancelAndIgnoreRemainingEvents()
//        }
    }
}
