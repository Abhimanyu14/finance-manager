package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData
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
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
) : SourcesScreenViewModel, ViewModel() {
    private val defaultSourceId: Flow<Int?> = myPreferencesRepository.getDefaultDataId().map {
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
                    icon = source.type.icon,
                    sourceId = source.id,
                    balance = source.balanceAmount.toString(),
                    name = source.name,
                    isDefault = isDefault,
                    isDeleteEnabled = !isDefaultSource(
                        source = source.name,
                    ) && deleteEnabled,
                    isExpanded = false,
                )
            }
    }.defaultListStateIn(
        scope = viewModelScope,
    )

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
            myPreferencesRepository.setDefaultSourceId(
                defaultSourceId = defaultSourceId,
            )
        }
    }
}
