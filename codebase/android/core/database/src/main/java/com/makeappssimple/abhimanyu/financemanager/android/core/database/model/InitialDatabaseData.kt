package com.makeappssimple.abhimanyu.financemanager.android.core.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Change Notes
 * 1. Any changes in this data class should also have corresponding changes in [initial_data.json]
 * 2. There changes do NOT need migrations as
 * they will be bundled with the new APK when any changes are done
 */
@Serializable
internal data class InitialDatabaseData(
    @SerialName("default_accounts")
    val defaultAccounts: Accounts,

    @SerialName("default_categories")
    val defaultCategories: Categories,

    @SerialName("default_transaction_for_values")
    val defaultTransactionForValues: TransactionForValues,

    @SerialName("emojis")
    val emojis: Emojis,
)

@Serializable
internal data class Accounts(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_accounts")
    val versionedAccounts: List<VersionedAccounts>,
)

@Serializable
internal data class VersionedAccounts(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("accounts_data")
    val accountsData: List<AccountEntity>,
)

@Serializable
internal data class Categories(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_categories")
    val versionedCategories: List<VersionedCategories>,
)

@Serializable
internal data class VersionedCategories(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("categories_data")
    val categoriesData: List<CategoryEntity>,
)

@Serializable
internal data class TransactionForValues(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("versioned_transaction_for_values")
    val versionedTransactionForValues: List<VersionedTransactionForValues>,
)

@Serializable
internal data class VersionedTransactionForValues(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("transaction_for_values_data")
    val transactionForValuesData: List<TransactionForEntity>,
)

@Serializable
internal data class Emojis(
    @SerialName("version_number")
    val versionNumber: Int,

    @SerialName("emojis_data")
    val emojisData: List<EmojiEntity>,
)
