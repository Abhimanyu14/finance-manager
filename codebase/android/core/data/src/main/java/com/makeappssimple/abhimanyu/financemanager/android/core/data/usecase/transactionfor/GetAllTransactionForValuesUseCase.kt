package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor

public interface GetAllTransactionForValuesUseCase {
    public suspend operator fun invoke(): List<TransactionFor>
}

public class GetAllTransactionForValuesUseCaseImpl(
    private val transactionForRepository: TransactionForRepository,
) : GetAllTransactionForValuesUseCase {
    override suspend operator fun invoke(): List<TransactionFor> {
        return transactionForRepository.getAllTransactionForValues()
    }
}
