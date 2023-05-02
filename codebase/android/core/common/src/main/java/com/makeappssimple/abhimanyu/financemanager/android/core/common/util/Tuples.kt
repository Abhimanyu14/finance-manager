package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import java.io.Serializable

/**
 * Expanding [Pair] and [Triple]
 */
data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {

    /**
     * Returns string representation of the [Quadruple] including its
     * [first], [second], [third] and [fourth] values.
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth)"
    }
}

/**
 * Converts this quadruple into a list.
 */
fun <T> Quadruple<T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth)
}
