package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.buildconfig.BuildConfigUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LoggerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManagerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
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

    @Inject
    private lateinit var dispatcherProvider: DispatcherProvider

    private val testCoroutineScope = TestScope(
        context = dispatcherProvider.io + Job(),
    )


    private val getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase = mock()
    private val navigationManager: NavigationManager = NavigationManagerImpl(
        coroutineScope = testCoroutineScope,
    )
    private var buildConfigUtil: BuildConfigUtil = BuildConfigUtilImpl()
    private var logger: Logger = LoggerImpl(
        buildConfigUtil = buildConfigUtil,
    )
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase =
        mock()
    private val dataStore: MyDataStore = mock()
    private val deleteSourcesUseCase: DeleteSourcesUseCase = mock()
    private val source: Source = mock()

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
