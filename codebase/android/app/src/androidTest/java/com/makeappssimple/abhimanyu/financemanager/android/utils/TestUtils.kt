package com.makeappssimple.abhimanyu.financemanager.android.utils

import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType

const val timeInMillis_01_JUN_2022 = 1654021800000
const val ONE_HOUR = 60L * 60 * 1000
const val ONE_DAY = ONE_HOUR * 24
const val SEVEN_DAYS = ONE_HOUR * 24
const val THIRTY_DAYS = ONE_DAY * 30

fun getTestAmount(): Amount {
    val value = (1..100).random()
    return Amount(
        value = value.toLong(),
    )
}

fun getTestCategory(): Category {
    return Category(
        emoji = "emoji",
        title = "title",
        transactionType = TransactionType.EXPENSE,
    )
}

fun getTestCategories(): Array<Category> {
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

fun getTestEmoji(): EmojiLocalEntity {
    return EmojiLocalEntity(
        character = "character",
        codePoint = "codePoint",
        group = "group",
        unicodeName = "unicodeName",
    )
}

fun getTestEmojis(): Array<EmojiLocalEntity> {
    return arrayOf(
        EmojiLocalEntity(
            character = "character",
            codePoint = "codePoint",
            group = "group",
            unicodeName = "unicodeName",
        ),
        EmojiLocalEntity(
            character = "character 1",
            codePoint = "codePoint 1",
            group = "group 1",
            unicodeName = "unicodeName 1",
        ),
    )
}

fun getTestSource(): Source {
    return Source(
        name = "Axis",
    )
}

fun getTestSources(): Array<Source> {
    return arrayOf(
        Source(
            name = "Axis",
        ),
        Source(
            name = "SBI",
        ),
    )
}

fun getTestTransaction(): Transaction {
    return Transaction(
        amount = getTestAmount(),
        title = "title",
        creationTimestamp = timeInMillis_01_JUN_2022,
        transactionTimestamp = timeInMillis_01_JUN_2022,
    )
}

fun getTestTransactions(
    size: Int = 2,
    frequency: Long,
): Array<Transaction> {
    val result = Array(size) { index ->
        Transaction(
            id = index + 1,
            amount = getTestAmount(),
            title = "title $index",
            creationTimestamp = timeInMillis_01_JUN_2022,
            transactionTimestamp = timeInMillis_01_JUN_2022 - (index * frequency),
        )
    }
    return result
}
