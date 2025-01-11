package com.makeappssimple.abhimanyu.financemanager.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation.accountsNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.navigation.analysisNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.navigation.categoriesNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.navigation.homeNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.navigation.settingsNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation.transactionForNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.transactionsNavGraph

@Composable
internal fun MyNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        accountsNavGraph()
        analysisNavGraph()
        categoriesNavGraph()
        homeNavGraph()
        settingsNavGraph()
        transactionForNavGraph()
        transactionsNavGraph()
    }
}
