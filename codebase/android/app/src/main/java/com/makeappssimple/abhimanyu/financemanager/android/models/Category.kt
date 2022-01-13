package com.makeappssimple.abhimanyu.financemanager.android.models

data class Category(
    val id: Int,
    val title: String,
    val description: String = "",
    val children: List<Category>? = null,
)
