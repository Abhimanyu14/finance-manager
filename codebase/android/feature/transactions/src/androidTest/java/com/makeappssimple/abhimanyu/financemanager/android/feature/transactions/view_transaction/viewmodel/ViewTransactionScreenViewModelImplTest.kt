package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.turbineScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.fake.FakeMyPreferencesRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.fake.FakeTransactionRepositoryImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.DeleteTransactionUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCaseImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.fake.FakeMyLoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake.FakeNavigationManagerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ViewTransactionScreenViewModelImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringDecoder: StringDecoder
    private lateinit var myLogger: MyLogger
    private lateinit var navigationManager: NavigationManager
    private lateinit var dateTimeUtil: DateTimeUtil
    private lateinit var deleteTransactionUseCase: DeleteTransactionUseCase
    private lateinit var getTransactionDataUseCase: GetTransactionDataUseCase

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    private lateinit var viewTransactionScreenViewModelImpl: ViewTransactionScreenViewModelImpl

    @Before
    fun setUp() {
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
    fun tearDown() {
    }

    @Test
    fun navigateToAddTransactionScreenTest() {
    }

    @Test
    fun navigateToEditTransactionScreenTest() {
    }

    @Test
    fun navigateUpTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // viewTransactionScreenViewModelImpl.navigateUp()

            val receiver = viewTransactionScreenViewModelImpl.navigationManager.command.testIn(
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
            dateTimeUtil = dateTimeUtil,
            deleteTransactionUseCase = deleteTransactionUseCase,
            dispatcherProvider = dispatcherProvider,
            getTransactionDataUseCase = getTransactionDataUseCase,
            navigationManager = navigationManager,
        )
    }
}
