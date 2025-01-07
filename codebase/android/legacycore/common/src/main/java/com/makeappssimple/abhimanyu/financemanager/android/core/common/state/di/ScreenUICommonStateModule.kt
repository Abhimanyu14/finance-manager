package com.makeappssimple.abhimanyu.financemanager.android.core.common.state.di

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.state.refresh.ScreenUIStateRefreshImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public class ScreenUICommonStateModule {
    @Singleton
    @Provides
    public fun providesScreenUICommonState(
        @ApplicationScope coroutineScope: CoroutineScope,
    ): ScreenUICommonState {
        return ScreenUICommonStateImpl(
            screenUIStateLoading = ScreenUIStateLoadingImpl(
                screenUIStateRefresh = ScreenUIStateRefreshImpl(
                    coroutineScope = coroutineScope,
                ),
            ),
        )
    }
}
