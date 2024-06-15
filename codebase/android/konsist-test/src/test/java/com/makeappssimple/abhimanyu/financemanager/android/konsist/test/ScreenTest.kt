package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotationNamed
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class ScreenTest {
    @Test
    fun `Composables with 'Screen' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("Screen")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `Composables with 'ScreenUI' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUI")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }
}
