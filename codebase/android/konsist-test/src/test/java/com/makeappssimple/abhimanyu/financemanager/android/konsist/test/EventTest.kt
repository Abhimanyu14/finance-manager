package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class EventTest {
    @Test
    fun `classes with 'ScreenUIEvent' suffix should reside in 'event' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenUIEvent")
            .assertTrue {
                it.resideInPackage("..event..")
            }
    }

    @Test
    fun `classes with 'ScreenUIEventHandler' suffix should reside in 'event' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("ScreenUIEventHandler")
            .assertTrue {
                it.resideInPackage("..event..")
            }
    }
}
