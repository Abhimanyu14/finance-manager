package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import com.makeappssimple.abhimanyu.financemanager.android.data.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    sourceRepository: SourceRepository,
    val navigationManager: NavigationManager,
) : BaseViewModel() {
    val sources: Flow<List<Source>> = sourceRepository.sources

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
