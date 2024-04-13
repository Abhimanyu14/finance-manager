package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.AmountEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

public const val timeInMillis_01_JUN_2022: Long = 1654021800000
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

public fun getTestAmount(): AmountEntity {
    return AmountEntity(
        value = 45,
    )
}

public fun getTestCategory(): CategoryEntity {
    return CategoryEntity(
        emoji = "emoji",
        title = "title",
        transactionType = TransactionType.EXPENSE,
    )
}

public fun getTestCategories(): Array<CategoryEntity> {
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

public fun getTestAccount(): AccountEntity {
    return AccountEntity(
        name = "Axis",
    )
}

public fun getTestAccounts(): Array<AccountEntity> {
    return arrayOf(
        AccountEntity(
            name = "Axis",
        ),
        AccountEntity(
            name = "SBI",
        ),
    )
}

public fun getTestTransaction(): TransactionEntity {
    return TransactionEntity(
        amount = getTestAmount(),
        title = "title",
        creationTimestamp = timeInMillis_01_JUN_2022_00_00_00,
        transactionTimestamp = timeInMillis_01_JUN_2022_00_00_00,
    )
}

public fun getTestTransactions(): Array<TransactionEntity> {
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

public fun getTestTransactions(
    size: Int = 2,
    frequency: Long,
): Array<TransactionEntity> {
    val result = Array(size) { index ->
        TransactionEntity(
            id = index + 1,
            amount = getTestAmount(),
            title = "title $index",
            creationTimestamp = timeInMillis_01_JUN_2022,
            transactionTimestamp = timeInMillis_01_JUN_2022 - (index * frequency),
        )
    }
    return result
}

public fun getReadableDateAndTime(
    timestamp: Long,
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return Instant
        .ofEpochMilli(timestamp)
        .formattedReadableDateAndTime(
            zoneId = zoneId,
        )
}

public fun Instant.formattedReadableDateAndTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return "${formattedDate(zoneId)} at ${formattedTime(zoneId)}"
}

public fun Instant.formattedDate(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return DateTimeFormatter
        .ofPattern("dd MMM, yyyy")
        .withZone(zoneId)
        .format(this)
}

public fun Instant.formattedTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
): String {
    return DateTimeFormatter
        .ofPattern("hh:mm a")
        .withZone(zoneId)
        .format(this)
        .replace("am", "AM")
        .replace("pm", "PM")
}
