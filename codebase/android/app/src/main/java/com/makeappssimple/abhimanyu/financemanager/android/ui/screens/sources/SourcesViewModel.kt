package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface SourcesViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val sources: Flow<List<Source>>
    val sourcesTotalBalanceAmountValue: Flow<Long>

    fun deleteSource(
        id: Int,
    )

    fun insertTransaction(
        amountValue: Long,
        sourceTo: Source,
    )
}
