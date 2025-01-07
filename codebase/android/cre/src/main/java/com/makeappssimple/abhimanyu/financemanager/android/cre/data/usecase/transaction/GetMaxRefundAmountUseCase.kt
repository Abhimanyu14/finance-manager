package com.makeappssimple.abhimanyu.financemanager.android.cre.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.minus
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.plus
import javax.inject.Inject

public class GetMaxRefundAmountUseCase @Inject constructor(
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
) {
    public suspend operator fun invoke(
        id: Int,
    ): Amount? {
        val originalTransactionData: TransactionData = getTransactionDataUseCase(
            id = id,
        ) ?: return null

        val refundTransactionIds = originalTransactionData.transaction.refundTransactionIds
        if (refundTransactionIds.isNullOrEmpty()) {
            return originalTransactionData.transaction.amount
        }

        val refundedAmountCalculated: Amount? = calculateRefundedAmount(
            refundTransactionIds = refundTransactionIds,
        )
        return if (refundedAmountCalculated.isNotNull()) {
            originalTransactionData.transaction.amount.minus(refundedAmountCalculated)
        } else {
            originalTransactionData.transaction.amount
        }
    }

    private suspend fun calculateRefundedAmount(
        refundTransactionIds: List<Int>,
    ): Amount? {
        var refundedAmountCalculated: Amount? = null
        for (refundTransactionId in refundTransactionIds) {
            val refundTransactionData = getTransactionDataUseCase(
                id = refundTransactionId,
            ) ?: continue
            val refundTransactionAmount = refundTransactionData.transaction.amount
            refundedAmountCalculated = refundedAmountCalculated?.plus(refundTransactionAmount)
                ?: refundTransactionAmount
        }
        return refundedAmountCalculated
    }
}
