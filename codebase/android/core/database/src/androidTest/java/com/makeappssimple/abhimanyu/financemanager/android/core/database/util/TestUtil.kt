package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Emoji
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

const val timeInMillis_01_JUN_2022 = 1654021800000
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

fun getTestEmoji(): Emoji {
    return Emoji(
        character = "character",
        codePoint = "codePoint",
        group = "group",
        unicodeName = "unicodeName",
    )
}

fun getTestEmojis(): Array<Emoji> {
    return arrayOf(
        Emoji(
            character = "character",
            codePoint = "codePoint",
            group = "group",
            unicodeName = "unicodeName",
        ),
        Emoji(
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
        creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
    )
}

fun getTestTransactions(): Array<Transaction> {
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

fun getReadableDateAndTime(
    timestamp: Long,
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedReadableDateAndTime(
            zoneId = zoneId,
        )
}

fun Instant.formattedReadableDateAndTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return "${formattedDate(zoneId)} at ${formattedTime(zoneId)}"
}

fun Instant.formattedDate(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

fun Instant.formattedTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}
