package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.DeleteAccountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAccountsTotalMinimumBalanceAmountValueUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfAccountIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManagerImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestScope
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import javax.inject.Inject

class AccountsScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Inject
    private lateinit var dispatcherProvider: DispatcherProvider

    private val testCoroutineScope = TestScope(
        context = dispatcherProvider.io + Job(),
    )

    private val getAllAccountsFlowUseCase: GetAllAccountsFlowUseCase = mock()
    private val getAccountsTotalBalanceAmountValueUseCase: GetAccountsTotalBalanceAmountValueUseCase =
        mock()
    private val getAccountsTotalMinimumBalanceAmountValueUseCase: GetAccountsTotalMinimumBalanceAmountValueUseCase =
        mock()
    private val navigationManager: NavigationManager = NavigationManagerImpl(
        coroutineScope = testCoroutineScope,
    )
    private val checkIfAccountIsUsedInTransactionsUseCase: CheckIfAccountIsUsedInTransactionsUseCase =
        mock()
    private val myPreferencesRepository: MyPreferencesRepository = mock()
    private val deleteAccountUseCase: DeleteAccountUseCase = mock()

    private lateinit var accountsScreenViewModel: AccountsScreenViewModel

    @Before
    fun setUp() {
        accountsScreenViewModel = AccountsScreenViewModelImpl(
            getAllAccountsFlowUseCase = getAllAccountsFlowUseCase,
            getAccountsTotalBalanceAmountValueUseCase = getAccountsTotalBalanceAmountValueUseCase,
            getAccountsTotalMinimumBalanceAmountValueUseCase = getAccountsTotalMinimumBalanceAmountValueUseCase,
            checkIfAccountIsUsedInTransactionsUseCase = checkIfAccountIsUsedInTransactionsUseCase,
            deleteAccountUseCase = deleteAccountUseCase,
            dispatcherProvider = dispatcherProvider,
            myPreferencesRepository = myPreferencesRepository,
            navigationManager = navigationManager,
        )
    }

    @Ignore
    @Test
    fun deleteAccount() {
        getAllAccountsFlowUseCase.stub {
            onBlocking {
                invoke()
            }.doReturn(
                flow {
                    emptyList<Account>()
                }
            )
        }

//        sourcesScreenViewModel.deleteAccount(
//            account = account,
//        )
    }
}
