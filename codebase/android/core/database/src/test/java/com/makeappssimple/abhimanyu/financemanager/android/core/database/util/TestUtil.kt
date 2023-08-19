package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

const val timeInMillis_01_JAN_2022_00_00_00 = 1640975400000 // 01-01-2022 00:00:00 IST
const val timeInMillis_01_JUN_2022_00_00_00 = 1654021800000 // 01-06-2022 00:00:00 IST
const val timeInMillis_01_JUN_2022_23_59_59 = 1654108199000 // 01-06-2022 23:59:59 IST
const val timeInMillis_02_JUN_2022_00_00_00 = 1654108200000 // 02-06-2022 00:00:00 IST
const val timeInMillis_02_JUN_2022_23_59_59 = 1654194599000 // 02-06-2022 23:59:59 IST
const val timeInMillis_30_JUN_2022_23_59_59 = 1656613799000 // 30-06-2022 23:59:59 IST
const val timeInMillis_31_DEC_2022_23_59_59 = 1672511399000 // 31-12-2022 23:59:59 IST
const val ONE_HOUR = 60L * 60 * 1000
const val ONE_DAY = ONE_HOUR * 24
const val SEVEN_DAYS = ONE_HOUR * 24
const val THIRTY_DAYS = ONE_DAY * 30

fun getTestAmount(): AmountEntity {
    return AmountEntity(
        value = 45,
    )
}

fun getTestCategory(): CategoryEntity {
    return CategoryEntity(
        emoji = "emoji",
        title = "title",
        transactionType = TransactionType.EXPENSE,
    )
}

fun getTestCategories(): Array<CategoryEntity> {
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

fun getTestAccount(): AccountEntity {
    return AccountEntity(
        name = "Axis",
    )
}

fun getTestSources(): Array<AccountEntity> {
    return arrayOf(
        AccountEntity(
            name = "Axis",
        ),
        AccountEntity(
            name = "SBI",
        ),
    )
}

fun getTestTransaction(): TransactionEntity {
    return TransactionEntity(
        amount = getTestAmount(),
        title = "title",
        creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
    )
}

fun getTestTransactions(): Array<TransactionEntity> {
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

fun getTestTransactionForValues(): Array<TransactionForEntity> {
    return arrayOf(
        TransactionForEntity(
            title = "Self"
        ),
        TransactionForEntity(
            title = "Family"
        ),
    )
}
