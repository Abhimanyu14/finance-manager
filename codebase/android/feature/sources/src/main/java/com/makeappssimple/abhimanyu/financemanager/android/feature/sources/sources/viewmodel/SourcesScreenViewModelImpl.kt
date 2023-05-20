package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.listitem.SourcesListItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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
    private val defaultSourceId: Flow<Int?> = dataStore.getDefaultDataId().map {
        it?.source
    }
    private val allSourcesFlow: Flow<List<Source>> = getAllSourcesFlowUseCase()
    override val sourcesListItemDataList: StateFlow<List<SourcesListItemData>> = combine(
        flow = defaultSourceId,
        flow2 = allSourcesFlow,
    ) { defaultSourceId, allSourcesFlow ->
        allSourcesFlow
            .sortedWith(
                comparator = compareBy<Source> { source ->
                    source.type.sortOrder
                }.thenByDescending { source ->
                    source.balanceAmount.value
                }
            ).map { source ->
                val deleteEnabled = !checkIfSourceIsUsedInTransactionsUseCase(
                    sourceId = source.id,
                )
                val isDefault = if (defaultSourceId.isNull()) {
                    isDefaultSource(
                        source = source.name,
                    )
                } else {
                    defaultSourceId == source.id
                }
                SourcesListItemData(
                    source = source,
                    isExpanded = false,
                    isDefault = isDefault,
                    isDeleteEnabled = !isDefaultSource(
                        source = source.name,
                    ) && deleteEnabled,
                )
            }
    }.defaultListStateIn(
        scope = viewModelScope,
    )


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
