package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SourcesScreenViewModelImpl @Inject constructor(
    getSourcesUseCase: GetSourcesUseCase,
    override val navigationManager: NavigationManager,
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase,
    private val dataStore: MyDataStore,
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : SourcesScreenViewModel, ViewModel() {
    override val sources: StateFlow<List<Source>> = getSourcesUseCase()
        .map {
            it.sortedWith(
                comparator = compareBy { source ->
                    source.type.sortOrder
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )
    override val sourcesIsUsedInTransactions: Flow<List<Boolean>> = sources
        .map {
            it.map { source ->
                checkIfSourceIsUsedInTransactionsUseCase(
                    sourceId = source.id,
                )
            }
        }
    override val defaultSourceId: Flow<Int?> = dataStore.getDefaultSourceIdFromDataStore()

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun deleteSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteSourceUseCase(
                id = id,
            )
        }
    }

    override fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            dataStore.setDefaultSourceIdInDataStore(
                defaultSourceId = defaultSourceId,
            )
        }
    }
}
