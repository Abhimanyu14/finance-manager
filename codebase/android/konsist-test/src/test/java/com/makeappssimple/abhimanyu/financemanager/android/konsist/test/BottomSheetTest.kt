package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class BottomSheetTest {
    @Test
    fun `classes with 'ScreenBottomSheetType' suffix should reside in 'bottomsheet' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenBottomSheetType")
            .assertTrue {
                it.resideInPackage("..bottomsheet..")
            }
    }
}
