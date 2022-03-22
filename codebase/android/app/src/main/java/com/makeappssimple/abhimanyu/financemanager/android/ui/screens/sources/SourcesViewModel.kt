package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
) : BaseViewModel() {
    val sources: Flow<List<Source>> = sourceRepository.sources
    val sourcesTotalBalanceAmountValue = flow {
        sourceRepository.sources.collectIndexed { _, sources ->
            emit(
                value = sources.sumOf { source ->
                    source.balanceAmount.value
                },
            )
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun deleteSource(
        id: Int,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            sourceRepository.deleteSource(
                id = id,
            )
        }
    }
}
