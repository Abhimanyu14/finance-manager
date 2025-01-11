package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeLogKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake.FakeNavigationKitImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
internal class ViewTransactionScreenViewModelTest {
    @get:Rule(order = 0)
    internal var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    internal val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringDecoder: StringDecoder
    private lateinit var logKit: LogKit
    private lateinit var navigationKit: NavigationKit
    private lateinit var dateTimeKit: DateTimeKit
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase
    private lateinit var getTransactionDataUseCase: GetTransactionDataUseCase

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    private lateinit var viewTransactionScreenViewModel: ViewTransactionScreenViewModel

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        stringDecoder = StringDecoderImpl()
        logKit = FakeLogKitImpl()
        dateTimeKit = DateTimeKitImpl()
        deleteTransactionUseCase = DeleteTransactionUseCase(
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            transactionRepository = FakeTransactionRepositoryImpl(),
        )
        getTransactionDataUseCase = GetTransactionDataUseCase(
            transactionRepository = FakeTransactionRepositoryImpl(),
        )
    }

    @After
    internal fun tearDown() {
    }

    @Test
    fun navigateToAddTransactionScreenTest() {
    }

    @Test
    fun navigateToEditTransactionScreenTest() {
    }

    @Test
    @Ignore("Fix later")
    internal fun navigateUpTest() = runTest {
        navigationKit = FakeNavigationKitImpl()
        initViewModel()

        turbineScope {
            // viewTransactionScreenViewModelImpl.navigateUp()

            val receiver = viewTransactionScreenViewModel.navigator.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    private fun initViewModel() {
        viewTransactionScreenViewModel = ViewTransactionScreenViewModel(
            savedStateHandle = savedStateHandle,
            stringDecoder = stringDecoder,
            dateTimeUtil = dateTimeKit,
            deleteTransactionUseCase = deleteTransactionUseCase,
            getTransactionDataUseCase = getTransactionDataUseCase,
            navigator = navigationKit,
        )
    }
}
