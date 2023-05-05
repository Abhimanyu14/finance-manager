package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class SourcesScreenViewModelImpl @Inject constructor(
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase,
    private val dataStore: MyDataStore,
    private val deleteSourcesUseCase: DeleteSourcesUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : SourcesScreenViewModel, ViewModel() {
    override val sources: StateFlow<List<Source>> = getAllSourcesFlowUseCase()
        .map {
            it.sortedWith(
                comparator = compareBy<Source> { source ->
                    source.type.sortOrder
                }.thenByDescending { source ->
                    source.balanceAmount.value
                }
            )
        }.defaultListStateIn(
            scope = viewModelScope,
        )
    override val sourcesIsUsedInTransactions: Flow<List<Boolean>> = sources
        .map {
            it.map { source ->
                checkIfSourceIsUsedInTransactionsUseCase(
                    sourceId = source.id,
                )
            }
        }
    override val defaultSourceId: Flow<Int?> = dataStore.getDefaultSourceId()

    override fun deleteSource(
        source: Source,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteSourcesUseCase(
                source,
            )
        }
    }

    override fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            dataStore.setDefaultSourceId(
                defaultSourceId = defaultSourceId,
            )
        }
    }
}
