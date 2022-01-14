package com.makeappssimple.abhimanyu.financemanager.android.models

data class Category(
    val parentCategory: Category? = null,
    val id: Int,
    val subCategories: List<Category>? = null,
    val description: String = "",
    val title: String,
)
