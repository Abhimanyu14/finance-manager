@file:Suppress("TopLevelPropertyNaming", "UnderscoresInNumericLiterals")

package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

internal const val timeInMillis_01_JAN_2022_00_00_00: Long =
    1640975400000 // 01-01-2022 00:00:00 IST
internal const val timeInMillis_01_JUN_2022_00_00_00: Long =
    1654021800000 // 01-06-2022 00:00:00 IST
internal const val timeInMillis_01_JUN_2022_23_59_59: Long =
    1654108199000 // 01-06-2022 23:59:59 IST
internal const val timeInMillis_02_JUN_2022_00_00_00: Long =
    1654108200000 // 02-06-2022 00:00:00 IST
internal const val timeInMillis_02_JUN_2022_23_59_59: Long =
    1654194599000 // 02-06-2022 23:59:59 IST
internal const val timeInMillis_30_JUN_2022_23_59_59: Long =
    1656613799000 // 30-06-2022 23:59:59 IST
internal const val timeInMillis_31_DEC_2022_23_59_59: Long =
    1672511399000 // 31-12-2022 23:59:59 IST
internal const val ONE_HOUR: Long = 60L * 60 * 1000
internal const val ONE_DAY: Long = ONE_HOUR * 24
internal const val SEVEN_DAYS: Long = ONE_HOUR * 24
internal const val THIRTY_DAYS: Long = ONE_DAY * 30

internal fun getTestAmount(): AmountEntity {
    return AmountEntity(
        value = 45,
    )
}

internal fun getTestCategory(): CategoryEntity {
    return CategoryEntity(
        emoji = "emoji",
        title = "title",
        transactionType = TransactionType.EXPENSE,
    )
}

internal fun getTestCategories(): Array<CategoryEntity> {
    return arrayOf(
        CategoryEntity(
            emoji = "emoji",
            title = "title",
            transactionType = TransactionType.EXPENSE,
        ),
        CategoryEntity(
            emoji = "emoji 1",
            title = "title 1",
            transactionType = TransactionType.INCOME,
        ),
    )
}

internal fun getTestAccount(): AccountEntity {
    return AccountEntity(
        name = "Axis",
    )
}

internal fun getTestSources(): Array<AccountEntity> {
    return arrayOf(
        AccountEntity(
            name = "Axis",
        ),
        AccountEntity(
            name = "SBI",
        ),
    )
}

internal fun getTestTransaction(): TransactionEntity {
    return TransactionEntity(
        amount = getTestAmount(),
        title = "title",
        creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
    )
}

internal fun getTestTransactions(): Array<TransactionEntity> {
    return arrayOf(
        TransactionEntity(
            amount = getTestAmount(),
            title = "title",
            creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
            transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        ),
        TransactionEntity(
            amount = getTestAmount(),
            title = "title 1",
            creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
            transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        ),
    )
}

internal fun getTestTransactionForValues(): Array<TransactionForEntity> {
    return arrayOf(
        TransactionForEntity(
            title = "Self"
        ),
        TransactionForEntity(
            title = "Family"
        ),
    )
}
