package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class SourcesScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase = mock()
    private val navigationManager: NavigationManager = mock()
    private val logger: Logger = LoggerImpl()
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase =
        mock()
    private val dataStore: MyDataStore = mock()
    private val deleteSourcesUseCase: DeleteSourcesUseCase = mock()
    private val source: Source = mock()
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )

    private lateinit var sourcesScreenViewModel: SourcesScreenViewModel

    @Before
    fun setUp() {
        sourcesScreenViewModel = SourcesScreenViewModelImpl(
            getAllSourcesFlowUseCase = getAllSourcesFlowUseCase,
            logger = logger,
            navigationManager = navigationManager,
            checkIfSourceIsUsedInTransactionsUseCase = checkIfSourceIsUsedInTransactionsUseCase,
            dataStore = dataStore,
            deleteSourcesUseCase = deleteSourcesUseCase,
            dispatcherProvider = dispatcherProvider,
        )
    }

    @Ignore
    @Test
    fun deleteSource() {
        getAllSourcesFlowUseCase.stub {
            onBlocking {
                invoke()
            }.doReturn(
                flow {
                    emptyList<Source>()
                }
            )
        }

        sourcesScreenViewModel.deleteSource(
            source = source,
        )
    }
}
