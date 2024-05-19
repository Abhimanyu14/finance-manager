package com.makeappssimple.abhimanyu.financemanager.android.konsist.test

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.Test

internal class ModuleTest {
    @Test
    fun `classes with 'Module' suffix should reside in 'di' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("Module")
            .assertTrue {
                it.resideInPackage("..di..")
            }
    }
}
