package com.makeappssimple.abhimanyu.financemanager.android

class LintTest {
    // We have a custom lint check bundled with :library
    // that this module depends on. The lint check looks
    // for mentions of "lint", which should trigger an
    // error
    val s = "lint"
    fun lint() { }
}
