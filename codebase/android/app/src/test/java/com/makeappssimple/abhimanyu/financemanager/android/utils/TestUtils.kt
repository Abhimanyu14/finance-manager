package com.makeappssimple.abhimanyu.financemanager.android.utils

import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.emoji.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType

fun getTestAmount(): Amount {
    return Amount(
        value = 45,
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
        creationTimestamp = System.currentTimeMillis(),
        transactionTimestamp = System.currentTimeMillis(),
    )
}

fun getTestTransactions(): Array<Transaction> {
    return arrayOf(
        Transaction(
            amount = getTestAmount(),
            title = "title",
            creationTimestamp = System.currentTimeMillis(),
            transactionTimestamp = System.currentTimeMillis(),
        ),
        Transaction(
            amount = getTestAmount(),
            title = "title 1",
            creationTimestamp = System.currentTimeMillis(),
            transactionTimestamp = System.currentTimeMillis(),
        ),
    )
}
