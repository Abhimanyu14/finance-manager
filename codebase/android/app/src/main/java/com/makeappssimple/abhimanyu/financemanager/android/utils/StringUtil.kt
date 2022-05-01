package com.makeappssimple.abhimanyu.financemanager.android.utils

internal fun isDefaultCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Default",
        ignoreCase = true,
    )
}

internal fun isSalaryCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Salary",
        ignoreCase = true,
    )
}

internal fun isCashSource(
    source: String,
): Boolean {
    return source.contains(
        other = "Cash",
        ignoreCase = true,
    )
}
