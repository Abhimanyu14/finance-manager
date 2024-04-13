package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.viewmodel.SettingsScreenViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
public class SettingsScreenViewModelTest {
    @get:Rule(order = 0)
    public var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @Inject
    public lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    public fun setUp() {
    }

    @After
    public fun tearDown() {
    }
}
