package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val backupDataUseCase: BackupDataUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
) : HomeScreenViewModel, ViewModel() {
    private val homeListItemViewData: Flow<List<TransactionListItemData>> =
        getHomeListItemViewDataFromData()
    private val isBackupCardVisible: Flow<Boolean> = getIsBackupCardVisibleFromData()

    override val screenUIData: StateFlow<HomeScreenUIData?> = combine(
        isBackupCardVisible,
        homeListItemViewData,
    ) {
            isBackupCardVisible,
            homeListItemViewData,
        ->
        HomeScreenUIData(
            isBackupCardVisible = isBackupCardVisible,
            transactionListItemDataList = homeListItemViewData,
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun backupDataToDocument(
        uri: Uri,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                backupDataUseCase(
                    uri = uri,
                )
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    private fun getHomeListItemViewDataFromData(): Flow<List<TransactionListItemData>> {
        return getRecentTransactionDataFlowUseCase().map {
            it.map { listItem ->
                val amountColor: MyColor = listItem.transaction.getAmountTextColor()
                val amountText: String =
                    if (listItem.transaction.transactionType == TransactionType.INCOME ||
                        listItem.transaction.transactionType == TransactionType.EXPENSE ||
                        listItem.transaction.transactionType == TransactionType.ADJUSTMENT ||
                        listItem.transaction.transactionType == TransactionType.REFUND
                    ) {
                        listItem.transaction.amount.toSignedString(
                            isPositive = listItem.sourceTo.isNotNull(),
                            isNegative = listItem.sourceFrom.isNotNull(),
                        )
                    } else {
                        listItem.transaction.amount.toString()
                    }
                val dateAndTimeText: String = dateTimeUtil.getReadableDateAndTime(
                    timestamp = listItem.transaction.transactionTimestamp,
                )
                val emoji: String = when (listItem.transaction.transactionType) {
                    TransactionType.TRANSFER -> {
                        EmojiConstants.LEFT_RIGHT_ARROW
                    }

                    TransactionType.ADJUSTMENT -> {
                        EmojiConstants.EXPRESSIONLESS_FACE
                    }

                    else -> {
                        listItem.category?.emoji.orEmpty()
                    }
                }
                val sourceFromName = listItem.sourceFrom?.name
                val sourceToName = listItem.sourceTo?.name
                val title: String = listItem.transaction.title
                val transactionForText: String =
                    listItem.transactionFor.titleToDisplay

                TransactionListItemData(
                    transactionId = listItem.transaction.id,
                    amountColor = amountColor,
                    amountText = amountText,
                    dateAndTimeText = dateAndTimeText,
                    emoji = emoji,
                    sourceFromName = sourceFromName,
                    sourceToName = sourceToName,
                    title = title,
                    transactionForText = transactionForText,
                )
            }
        }
    }

    private fun getIsBackupCardVisibleFromData(): Flow<Boolean> {
        return myPreferencesRepository.getDataTimestamp().map {
            it.isNotNull() && it.lastBackup < it.lastChange
        }
    }
}
