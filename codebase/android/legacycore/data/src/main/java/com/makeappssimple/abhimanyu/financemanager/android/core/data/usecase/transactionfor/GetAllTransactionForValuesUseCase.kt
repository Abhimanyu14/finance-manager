package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetAllTransactionForValuesUseCase @Inject constructor(
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(): ImmutableList<TransactionFor> {
        return transactionForRepository.getAllTransactionForValues()
    }
}
