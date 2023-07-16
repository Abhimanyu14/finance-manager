package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationCommand
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class FakeNavigationManagerImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationManager {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigate(
        navigationCommand: NavigationCommand,
    ) {
        coroutineScope.launch {
            _command.emit(navigationCommand)
        }
    }
}
