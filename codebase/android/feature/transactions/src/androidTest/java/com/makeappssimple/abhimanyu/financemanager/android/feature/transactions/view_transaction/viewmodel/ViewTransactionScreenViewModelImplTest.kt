package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.CloseableCoroutineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake.FakeNavigatorImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
public class ViewTransactionScreenViewModelImplTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    public val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringDecoder: StringDecoder
    private lateinit var myLogger: MyLogger
    private lateinit var navigator: Navigator
    private lateinit var dateTimeUtil: DateTimeUtil
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase
    private lateinit var getTransactionDataUseCase: GetTransactionDataUseCase

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    @Inject
    public lateinit var closeableCoroutineScope: CloseableCoroutineScope

    private lateinit var viewTransactionScreenViewModelImpl: ViewTransactionScreenViewModelImpl

    @Before
    public fun setUp() {
        savedStateHandle = SavedStateHandle()
        stringDecoder = StringDecoderImpl()
        myLogger = FakeMyLoggerImpl()
        dateTimeUtil = DateTimeUtilImpl()
        deleteTransactionUseCase = DeleteTransactionUseCaseImpl(
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            transactionRepository = FakeTransactionRepositoryImpl(),
        )
        getTransactionDataUseCase = GetTransactionDataUseCaseImpl(
            transactionRepository = FakeTransactionRepositoryImpl(),
        )
    }

    @After
    public fun tearDown() {
    }

    @Test
    public fun navigateToAddTransactionScreenTest() {
    }

    @Test
    public fun navigateToEditTransactionScreenTest() {
    }

    @Test
    public fun navigateUpTest(): TestResult = runTest {
        navigator = FakeNavigatorImpl()
        initViewModel()

        turbineScope {
            // viewTransactionScreenViewModelImpl.navigateUp()

            val receiver = viewTransactionScreenViewModelImpl.navigator.command.testIn(
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
        viewTransactionScreenViewModelImpl = ViewTransactionScreenViewModelImpl(
            savedStateHandle = savedStateHandle,
            stringDecoder = stringDecoder,
            closeableCoroutineScope = closeableCoroutineScope,
            dateTimeUtil = dateTimeUtil,
            deleteTransactionUseCase = deleteTransactionUseCase,
            dispatcherProvider = dispatcherProvider,
            getTransactionDataUseCase = getTransactionDataUseCase,
            navigator = navigator,
        )
    }
}
