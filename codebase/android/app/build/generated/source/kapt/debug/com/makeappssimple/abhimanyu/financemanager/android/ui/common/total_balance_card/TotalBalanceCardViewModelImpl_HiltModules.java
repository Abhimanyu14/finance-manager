package com.makeappssimple.abhimanyu.financemanager.android.ui.common.total_balance_card;

import androidx.lifecycle.ViewModel;

import com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card.TotalBalanceCardViewModelImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.IntoSet;
import dagger.multibindings.StringKey;
import java.lang.String;

@OriginatingElement(
    topLevelClass = TotalBalanceCardViewModelImpl.class
)
public final class TotalBalanceCardViewModelImpl_HiltModules {
  private TotalBalanceCardViewModelImpl_HiltModules() {
  }

  @Module
  @InstallIn(ViewModelComponent.class)
  public abstract static class BindsModule {
    private BindsModule() {
    }

    @Binds
    @IntoMap
    @StringKey("com.makeappssimple.abhimanyu.financemanager.android.ui.common.total_balance_card.TotalBalanceCardViewModelImpl")
    @HiltViewModelMap
    public abstract ViewModel binds(TotalBalanceCardViewModelImpl vm);
  }

  @Module
  @InstallIn(ActivityRetainedComponent.class)
  public static final class KeyModule {
    private KeyModule() {
    }

    @Provides
    @IntoSet
    @HiltViewModelMap.KeySet
    public static String provide() {
      return "com.makeappssimple.abhimanyu.financemanager.android.ui.common.total_balance_card.TotalBalanceCardViewModelImpl";
    }
  }
}
