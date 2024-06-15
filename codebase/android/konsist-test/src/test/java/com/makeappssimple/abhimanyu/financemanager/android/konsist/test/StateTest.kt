package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotationNamed
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class StateTest {
    @Test
    fun `classes with 'ScreenUIState' suffix should reside in 'state' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenUIState")
            .assertTrue {
                it.resideInPackage("..state..")
            }
    }

    @Test
    fun `Composables with 'ScreenUIState' suffix should reside in 'state' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUIState")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..state..")
            }
    }

    @Test
    fun `classes with 'ScreenUIStateAndStateEvents' suffix should reside in 'state' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenUIStateAndStateEvents")
            .assertTrue {
                it.resideInPackage("..state..")
            }
    }

    @Test
    fun `classes with 'ScreenUIStateEvents' suffix should reside in 'state' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenUIStateEvents")
            .assertTrue {
                it.resideInPackage("..state..")
            }
    }
}
