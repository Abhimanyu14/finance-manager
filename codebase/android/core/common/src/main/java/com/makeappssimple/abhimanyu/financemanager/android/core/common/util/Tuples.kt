package com.makeappssimple.abhimanyu.financemanager.android.core.common.util

import java.io.Serializable

/**
 * Represents a collection of four values.
 *
 * See Also: [Pair] and [Triple].
 */
public data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {

    /**
     * Returns string representation of the [Quadruple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth)"
    }
}

/**
 * Converts this [Quadruple] into a list.
 */
public fun <T> Quadruple<T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth)
}

/**
 * Represents a collection of five values.
 */
public data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
) : Serializable {

    /**
     * Returns string representation of the [Quadruple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth)"
    }
}

/**
 * Converts this [Quintuple] into a list.
 */
public fun <T> Quintuple<T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth)
}

/**
 * Represents a collection of six values.
 */
public data class Sextuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
) : Serializable {

    /**
     * Returns string representation of the [Quadruple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth)"
    }
}

/**
 * Converts this [Quintuple] into a list.
 */
public fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth)
}
