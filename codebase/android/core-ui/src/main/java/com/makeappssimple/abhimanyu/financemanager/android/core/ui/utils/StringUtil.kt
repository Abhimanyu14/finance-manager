package com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils

fun isDefaultCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Default",
        ignoreCase = true,
    )
}

fun isSalaryCategory(
    category: String,
): Boolean {
    return category.contains(
        other = "Salary",
        ignoreCase = true,
    )
}

fun isCashSource(
    source: String,
): Boolean {
    return source.contains(
        other = "Cash",
        ignoreCase = true,
    )
}