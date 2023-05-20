package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetRecentTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.BackupDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class HomeScreenViewModelImpl @Inject constructor(
    dataStore: MyDataStore,
    getRecentTransactionDataFlowUseCase: GetRecentTransactionDataFlowUseCase,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val backupDataUseCase: BackupDataUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
) : HomeScreenViewModel, ViewModel() {
    override val homeListItemViewData: Flow<List<TransactionListItemData>> =
        getRecentTransactionDataFlowUseCase().map {
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
    override val showBackupCard: Flow<Boolean> = dataStore.getDataTimestamp().map {
        it.isNotNull() && it.lastBackup < it.lastChange
    }

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
}
