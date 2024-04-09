package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
class SettingsScreenViewModelTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }
}
