package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.source_details

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface SourceDetailsViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val source: Flow<Source?>

    fun getSource(
        id: Int,
    )

    fun deleteSource(
        id: Int,
    )

    fun insertTransaction(
        amountValue: Long,
        sourceTo: Source,
    )
}
