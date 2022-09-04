package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AddTransactionScreenViewModelImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dataStore: MyDataStore = mock()
    private val getCategoriesUseCase: GetCategoriesUseCase = mock()
    private val getSourcesUseCase: GetSourcesUseCase = mock()
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase = mock()
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase = mock()
    private val navigationManager: NavigationManager = mock()
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )
    private val getSourcesCountUseCase: GetSourcesCountUseCase = mock()
    private val insertTransactionsUseCase: InsertTransactionsUseCase = mock()
    private val updateSourcesUseCase: UpdateSourcesUseCase = mock()

    private lateinit var addTransactionScreenViewModel: AddTransactionScreenViewModel

    @Before
    fun setUp() {
        addTransactionScreenViewModel = AddTransactionScreenViewModelImpl(
            dataStore = dataStore,
            getCategoriesUseCase = getCategoriesUseCase,
            getSourcesUseCase = getSourcesUseCase,
            getTitleSuggestionsUseCase = getTitleSuggestionsUseCase,
            getAllTransactionForValuesUseCase = getAllTransactionForValuesUseCase,
            navigationManager = navigationManager,
            dispatcherProvider = dispatcherProvider,
            getSourcesCountUseCase = getSourcesCountUseCase,
            insertTransactionsUseCase = insertTransactionsUseCase,
            updateSourcesUseCase = updateSourcesUseCase,
        )
    }

    @After
    fun tearDown() {
    }

    @Ignore("Unable to fix this test - https://stackoverflow.com/questions/72121358/android-kotlin-unit-test-exception-in-thread-test-worker-coroutine1-java-l")
    @Test
    fun getTransactionTypesForNewTransaction(): Unit = runTest {
        // https://stackoverflow.com/a/61303126/9636037
//        getSourcesCountUseCase.stub {
//            onBlocking {
//                invoke()
//            }.doReturn(1)
//        }
        whenever(
            getSourcesCountUseCase.invoke()
        ).thenReturn(1)
        whenever(
            getSourcesUseCase.invoke()
        ).thenReturn(
            flow {
                emptyList<Source>()
            },
        )
        whenever(
            getCategoriesUseCase.invoke()
        ).thenReturn(
            flow {
                emptyList<Category>()
            },
        )

        val result: List<TransactionType> = addTransactionScreenViewModel
            .transactionTypesForNewTransaction.first()
        Assert.assertEquals(
            emptyArray<TransactionType>(),
            result,
        )
//        Assert.assertEquals(
//            arrayOf(
//                TransactionType.INCOME,
//                TransactionType.EXPENSE,
//                TransactionType.TRANSFER,
//            ),
//            result,
//        )
    }
}
