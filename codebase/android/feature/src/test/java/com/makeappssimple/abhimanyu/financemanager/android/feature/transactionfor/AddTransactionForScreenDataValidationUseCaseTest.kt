package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenTitleError
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.usecase.AddTransactionForScreenDataValidationUseCase
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert
import org.junit.Before
import org.junit.Test

internal class AddTransactionForScreenDataValidationUseCaseTest {
    private lateinit var useCase: AddTransactionForScreenDataValidationUseCase

    @Before
    fun setUp() {
        useCase = AddTransactionForScreenDataValidationUseCase()
    }

    @Test
    fun enteredTitleIsEmpty_returnsNoErrorWithCtaButtonEnabledFalse() {
        val result = useCase.invoke(
            allTransactionForValues = persistentListOf(),
            enteredTitle = "",
        )

        Assert.assertEquals(
            result.titleError,
            AddTransactionForScreenTitleError.None,
        )
        Assert.assertFalse(result.isCtaButtonEnabled)
    }

    @Test
    fun enteredTitleIsBlank_returnsNoErrorWithCtaButtonEnabledFalse() {
        val result = useCase.invoke(
            allTransactionForValues = persistentListOf(),
            enteredTitle = "   ",
        )

        Assert.assertEquals(
            result.titleError,
            AddTransactionForScreenTitleError.None,
        )
        Assert.assertFalse(result.isCtaButtonEnabled)
    }

    @Test
    fun enteredTitleIsNotBlankAndTransactionForTitleAlreadyUsed_returnsNoErrorWithCtaButtonEnabledFalse() {
        val result = useCase.invoke(
            allTransactionForValues = persistentListOf(
                TransactionFor(
                    title = "test",
                ),
            ),
            enteredTitle = "  Test ",
        )

        Assert.assertEquals(
            result.titleError,
            AddTransactionForScreenTitleError.TransactionForExists,
        )
        Assert.assertFalse(result.isCtaButtonEnabled)
    }

    @Test
    fun enteredTitleIsNotBlankAndTransactionForTitleNotUsed_returnsNoErrorWithCtaButtonEnabledFalse() {
        val result = useCase.invoke(
            allTransactionForValues = persistentListOf(),
            enteredTitle = "  Test ",
        )

        Assert.assertEquals(
            result.titleError,
            AddTransactionForScreenTitleError.None,
        )
        Assert.assertTrue(result.isCtaButtonEnabled)
    }
}
