package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface SettingsViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val categories: Flow<List<Category>>
    val emojis: Flow<List<EmojiLocalEntity>>
    val sources: Flow<List<Source>>
    val transactions: Flow<List<Transaction>>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun restoreDataFromDocument(
        uri: Uri,
    )
}
