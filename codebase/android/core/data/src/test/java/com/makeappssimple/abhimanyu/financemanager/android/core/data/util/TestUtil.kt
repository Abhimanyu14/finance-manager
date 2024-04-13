package com.makeappssimple.abhimanyu.financemanager.android.core.data.util

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

public const val timeInMillis_01_JAN_2022_00_00_00: Long = 1640975400000 // 01-01-2022 00:00:00 IST
public const val timeInMillis_01_JUN_2022_00_00_00: Long = 1654021800000 // 01-06-2022 00:00:00 IST
public const val timeInMillis_01_JUN_2022_23_59_59: Long = 1654108199000 // 01-06-2022 23:59:59 IST
public const val timeInMillis_02_JUN_2022_00_00_00: Long = 1654108200000 // 02-06-2022 00:00:00 IST
public const val timeInMillis_02_JUN_2022_23_59_59: Long = 1654194599000 // 02-06-2022 23:59:59 IST
public const val timeInMillis_30_JUN_2022_23_59_59: Long = 1656613799000 // 30-06-2022 23:59:59 IST
public const val timeInMillis_31_DEC_2022_23_59_59: Long = 1672511399000 // 31-12-2022 23:59:59 IST
public const val ONE_HOUR: Long = 60L * 60 * 1000
public const val ONE_DAY: Long = ONE_HOUR * 24
public const val SEVEN_DAYS: Long = ONE_HOUR * 24
public const val THIRTY_DAYS: Long = ONE_DAY * 30

public fun getTestAmount(): Amount {
    return Amount(
        value = 45,
    )
}

public fun getTestCategory(): Category {
    return Category(
        emoji = "emoji",
        title = "title",
        transactionType = TransactionType.EXPENSE,
    )
}

public fun getTestCategories(): Array<Category> {
    return arrayOf(
        Category(
            emoji = "emoji",
            title = "title",
            transactionType = TransactionType.EXPENSE,
        ),
        Category(
            emoji = "emoji 1",
            title = "title 1",
            transactionType = TransactionType.INCOME,
        ),
    )
}

public fun getTestAccount(): Account {
    return Account(
        name = "Axis",
    )
}

public fun getTestAccounts(): Array<Account> {
    return arrayOf(
        Account(
            name = "Axis",
        ),
        Account(
            name = "SBI",
        ),
    )
}

public fun getTestTransaction(): Transaction {
    return Transaction(
        amount = getTestAmount(),
        title = "title",
        creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
    )
}

public fun getTestTransactions(): Array<Transaction> {
    return arrayOf(
        Transaction(
            amount = getTestAmount(),
            title = "title",
            creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
            transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        ),
        Transaction(
            amount = getTestAmount(),
            title = "title 1",
            creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
            transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        ),
    )
}

public fun getTestTransactionForValues(): Array<TransactionFor> {
    return arrayOf(
        TransactionFor(
            title = "Self"
        ),
        TransactionFor(
            title = "Family"
        ),
    )
}
