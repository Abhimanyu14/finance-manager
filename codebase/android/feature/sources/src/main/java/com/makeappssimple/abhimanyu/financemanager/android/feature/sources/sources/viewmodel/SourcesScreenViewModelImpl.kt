package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.DeleteSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetSourcesTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfSourceIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.icon
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen.SourcesScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SourcesScreenViewModelImpl @Inject constructor(
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    getSourcesTotalBalanceAmountValueUseCase: GetSourcesTotalBalanceAmountValueUseCase,
    override val myLogger: MyLogger,
    private val checkIfSourceIsUsedInTransactionsUseCase: CheckIfSourceIsUsedInTransactionsUseCase,
    private val deleteSourceUseCase: DeleteSourceUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationManager: NavigationManager,
) : SourcesScreenViewModel, ViewModel() {
    private val defaultSourceId: Flow<Int?> = myPreferencesRepository.getDefaultDataId().map {
        it?.source
    }
    private val allSourcesFlow: Flow<List<Source>> = getAllSourcesFlowUseCase()
    private val sourcesTotalBalanceAmountValue: Flow<Long> =
        getSourcesTotalBalanceAmountValueUseCase()

    override val screenUIData: StateFlow<MyResult<SourcesScreenUIData>?> = combine(
        defaultSourceId,
        allSourcesFlow,
        sourcesTotalBalanceAmountValue,
    ) {
            defaultSourceId,
            allSourcesFlow,
            sourcesTotalBalanceAmountValue,
        ->
        val sourceTypes = SourceType.values().sortedBy {
            it.sortOrder
        }
        val groupedSources = allSourcesFlow.groupBy {
            it.type
        }
        val sourcesListItemDataList = mutableListOf<SourcesListItemData>()
        sourceTypes.forEach { sourceType ->
            val sortedSources = groupedSources[sourceType]?.sortedByDescending { source ->
                source.balanceAmount.value
            }
            val totalBalanceAmount = Amount(
                value = sortedSources?.sumOf {
                    it.balanceAmount.value
                }.orZero(),
            )
            sourcesListItemDataList.add(
                SourcesListItemData(
                    isHeading = true,
                    balance = "", //totalBalanceAmount.toString(),
                    name = sourceType.title,
                )
            )
            sourcesListItemDataList.addAll(
                groupedSources[sourceType]?.sortedByDescending { source ->
                    source.balanceAmount.value
                }?.map { source ->
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
                }.orEmpty()
            )
        }

        if (
            sourcesListItemDataList.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = SourcesScreenUIData(
                    sourcesListItemDataList = sourcesListItemDataList,
                    sourcesTotalBalanceAmountValue = sourcesTotalBalanceAmountValue,
                ),
            )
        }
    }.defaultObjectStateIn(
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

    override fun navigateToAddSourceScreen() {
        navigationManager.navigate(
            MyNavigationDirections.AddSource
        )
    }

    override fun navigateToEditSourceScreen(
        sourceId: Int,
    ) {
        navigationManager.navigate(
            MyNavigationDirections.EditSource(
                sourceId = sourceId,
            )
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
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
