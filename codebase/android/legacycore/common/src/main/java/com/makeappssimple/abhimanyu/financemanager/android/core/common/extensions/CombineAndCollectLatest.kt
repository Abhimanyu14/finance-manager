@file:Suppress("MethodOverloading")

package com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Decuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Duodecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Nonuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Octuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quadruple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quattuordecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quindecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quintuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Septendecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Septuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Sexdecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Sextuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Tredecuple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Undecuple
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

public suspend fun <T1, T2> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    action: suspend (value: Pair<T1, T2>) -> Unit,
) {
    return combine(flow, flow2) { value1, value2 ->
        Pair(value1, value2)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    action: suspend (value: Triple<T1, T2, T3>) -> Unit,
) {
    return combine(flow, flow2, flow3) { value1, value2, value3 ->
        Triple(value1, value2, value3)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    action: suspend (value: Quadruple<T1, T2, T3, T4>) -> Unit,
) {
    return combine(flow, flow2, flow3, flow4) { value1, value2, value3, value4 ->
        Quadruple(value1, value2, value3, value4)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    action: suspend (value: Quintuple<T1, T2, T3, T4, T5>) -> Unit,
) {
    return combine(flow, flow2, flow3, flow4, flow5) { value1, value2, value3, value4, value5 ->
        Quintuple(value1, value2, value3, value4, value5)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    action: suspend (value: Sextuple<T1, T2, T3, T4, T5, T6>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6
    ) { value1, value2, value3, value4, value5, value6 ->
        Sextuple(value1, value2, value3, value4, value5, value6)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    action: suspend (value: Septuple<T1, T2, T3, T4, T5, T6, T7>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7
    ) { value1, value2, value3, value4, value5, value6, value7 ->
        Septuple(value1, value2, value3, value4, value5, value6, value7)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    action: suspend (value: Octuple<T1, T2, T3, T4, T5, T6, T7, T8>) -> Unit,
) {
    return combine(flow, flow2, flow3, flow4, flow5, flow6, flow7, flow8) {
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
        ->
        Octuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    action: suspend (value: Nonuple<T1, T2, T3, T4, T5, T6, T7, T8, T9>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9 ->
        Nonuple(value1, value2, value3, value4, value5, value6, value7, value8, value9)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    action: suspend (value: Decuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10 ->
        Decuple(value1, value2, value3, value4, value5, value6, value7, value8, value9, value10)
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    action: suspend (value: Undecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11 ->
        Undecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    action: suspend (value: Duodecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12 ->
        Duodecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    flow13: Flow<T13>,
    action: suspend (value: Tredecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12,
        flow13
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13 ->
        Tredecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    flow13: Flow<T13>,
    flow14: Flow<T14>,
    action: suspend (value: Quattuordecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12,
        flow13,
        flow14
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14 ->
        Quattuordecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    flow13: Flow<T13>,
    flow14: Flow<T14>,
    flow15: Flow<T15>,
    action: suspend (value: Quindecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12,
        flow13,
        flow14,
        flow15
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15 ->
        Quindecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    flow13: Flow<T13>,
    flow14: Flow<T14>,
    flow15: Flow<T15>,
    flow16: Flow<T16>,
    action: suspend (value: Sexdecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12,
        flow13,
        flow14,
        flow15,
        flow16
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16 ->
        Sexdecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16
        )
    }.collectLatest {
        action(it)
    }
}

public suspend fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> combineAndCollectLatest(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    flow8: Flow<T8>,
    flow9: Flow<T9>,
    flow10: Flow<T10>,
    flow11: Flow<T11>,
    flow12: Flow<T12>,
    flow13: Flow<T13>,
    flow14: Flow<T14>,
    flow15: Flow<T15>,
    flow16: Flow<T16>,
    flow17: Flow<T17>,
    action: suspend (value: Septendecuple<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>) -> Unit,
) {
    return combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7,
        flow8,
        flow9,
        flow10,
        flow11,
        flow12,
        flow13,
        flow14,
        flow15,
        flow16,
        flow17,
    ) { value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12, value13, value14, value15, value16, value17 ->
        Septendecuple(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
        )
    }.collectLatest {
        action(it)
    }
}
