package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import javax.inject.Inject

public class GetAllTransactionForValuesUseCase @Inject constructor(
    private val transactionForRepository: TransactionForRepository,
) {
    public suspend operator fun invoke(): ImmutableList<TransactionFor> {
        return transactionForRepository.getAllTransactionForValues()
    }
}
