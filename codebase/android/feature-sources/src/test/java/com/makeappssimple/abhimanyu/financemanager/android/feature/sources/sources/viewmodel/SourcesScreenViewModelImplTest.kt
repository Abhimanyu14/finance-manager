package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.TestDispatcherProviderImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class SourcesScreenViewModelImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSourcesUseCase: GetSourcesUseCase = mock()
    private val navigationManager: NavigationManager = mock()
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase =
        mock()
    private val dataStore: MyDataStore = mock()
    private val deleteSourceUseCase: DeleteSourceUseCase = mock()
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = mainDispatcherRule.testDispatcher,
    )

    private lateinit var sourcesScreenViewModel: SourcesScreenViewModel

    @Before
    fun setUp() {
        sourcesScreenViewModel =
            SourcesScreenViewModelImpl(
                getSourcesUseCase = getSourcesUseCase,
                navigationManager = navigationManager,
                checkIfSourceIsUsedInTransactionsUseCase = checkIfSourceIsUsedInTransactionsUseCase,
                dataStore = dataStore,
                deleteSourceUseCase = deleteSourceUseCase,
                dispatcherProvider = dispatcherProvider,
            )
    }

    @Test
    fun deleteSource() {
        getSourcesUseCase.stub {
            onBlocking {
                invoke()
            }.doReturn(
                flow {
                    emptyList<Source>()
                }
            )
        }

        sourcesScreenViewModel.deleteSource(
            id = 0,
        )
    }
}
