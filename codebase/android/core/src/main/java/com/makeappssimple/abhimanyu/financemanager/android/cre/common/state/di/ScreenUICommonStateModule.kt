package com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.di

import com.makeappssimple.abhimanyu.financemanager.android.cre.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.common.ScreenUICommonState
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.common.ScreenUICommonStateImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.loading.ScreenUIStateLoadingImpl
import com.makeappssimple.abhimanyu.financemanager.android.cre.common.state.refresh.ScreenUIStateRefreshImpl
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
