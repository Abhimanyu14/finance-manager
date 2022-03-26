package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Amount
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSourceViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
) : BaseViewModel() {
    val sourceTypes = SourceType.values()
        .filter {
            it != SourceType.CASH
        }
    var balanceAmount by mutableStateOf(
        value = "",
    )
    var selectedSourceTypeIndex by mutableStateOf(
        value = sourceTypes.indexOf(
            element = SourceType.BANK,
        ),
    )
    var name by mutableStateOf(
        value = "",
    )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun insertSource() {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            sourceRepository.insertSource(
                source = Source(
                    balanceAmount = Amount(
                        value = balanceAmount.toLong(),
                    ),
                    type = sourceTypes[selectedSourceTypeIndex],
                    name = name,
                ),
            )
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    fun isValidSourceData(): Boolean {
        return name.isNotNullOrBlank() && balanceAmount.isNotNullOrBlank()
    }
}
