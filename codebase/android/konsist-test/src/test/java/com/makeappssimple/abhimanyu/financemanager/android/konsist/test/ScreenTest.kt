package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withAnnotationNamed
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class ScreenTest {
    @Test
    fun `classes with 'Screen' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("Screen")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `classes with 'ScreenUI' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUI")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `classes with 'ScreenUIData' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUIData")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `classes with 'ScreenUIEvent' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUIEvent")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `classes with 'ScreenUIState' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUIState")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }

    @Test
    fun `classes with 'ScreenUIStateAndEvents' suffix should reside in 'screen' package`() {
        Konsist.scopeFromProject()
            .functions()
            .withNameEndingWith("ScreenUIStateAndEvents")
            .withAnnotationNamed("Composable")
            .assertTrue {
                it.resideInPackage("..screen..")
            }
    }
}
