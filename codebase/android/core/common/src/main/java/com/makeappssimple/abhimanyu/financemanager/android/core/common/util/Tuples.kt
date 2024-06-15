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
     * Returns string representation of the [Quintuple].
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
     * Returns string representation of the [Sextuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth)"
    }
}

/**
 * Converts this [Sextuple] into a list.
 */
public fun <T> Sextuple<T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth)
}

/**
 * Represents a collection of seven values.
 */
public data class Septuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
) : Serializable {

    /**
     * Returns string representation of the [Septuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
    }
}

/**
 * Converts this [Septuple] into a list.
 */
public fun <T> Septuple<T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth, seventh)
}

/**
 * Represents a collection of eight values.
 */
public data class Octuple<out A, out B, out C, out D, out E, out F, out G, out H>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
) : Serializable {

    /**
     * Returns string representation of the [Octuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth)"
    }
}

/**
 * Converts this [Octuple] into a list.
 */
public fun <T> Octuple<T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth, seventh, eighth)
}

/**
 * Represents a collection of nine values.
 */
public data class Nonuple<out A, out B, out C, out D, out E, out F, out G, out H, out I>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
) : Serializable {

    /**
     * Returns string representation of the [Nonuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth)"
    }
}

/**
 * Converts this [Nonuple] into a list.
 */
public fun <T> Nonuple<T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)
}

/**
 * Represents a collection of ten values.
 */
public data class Decuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
) : Serializable {

    /**
     * Returns string representation of the [Decuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth)"
    }
}

/**
 * Converts this [Decuple] into a list.
 */
public fun <T> Decuple<T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth)
}

/**
 * Represents a collection of eleven values.
 */
public data class Undecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
) : Serializable {

    /**
     * Returns string representation of the [Undecuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh)"
    }
}

/**
 * Converts this [Undecuple] into a list.
 */
public fun <T> Undecuple<T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh
    )
}

/**
 * Represents a collection of twelve values.
 */
public data class Duodecuple<out A, out B, out C, out D, out E, out F, out G, out H, out I, out J, out K, out L>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G,
    val eighth: H,
    val ninth: I,
    val tenth: J,
    val eleventh: K,
    val twelfth: L,
) : Serializable {

    /**
     * Returns string representation of the [Duodecuple].
     */
    override fun toString(): String {
        return "($first, $second, $third, $fourth, $fifth, $sixth, $seventh, $eighth, $ninth, $tenth, $eleventh, $twelfth)"
    }
}

/**
 * Converts this [Duodecuple] into a list.
 */
public fun <T> Duodecuple<T, T, T, T, T, T, T, T, T, T, T, T>.toList(): List<T> {
    return listOf(
        first,
        second,
        third,
        fourth,
        fifth,
        sixth,
        seventh,
        eighth,
        ninth,
        tenth,
        eleventh,
        twelfth
    )
}