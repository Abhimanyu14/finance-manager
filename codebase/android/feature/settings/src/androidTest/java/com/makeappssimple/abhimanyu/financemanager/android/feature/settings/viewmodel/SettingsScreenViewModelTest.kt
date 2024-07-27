package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

/*
@Ignore("Fix Hilt")
@HiltAndroidTest
internal class SettingsScreenViewModelTest {
    @get:Rule
    internal val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

    private lateinit var appVersionUtil: AppVersionUtil
    private lateinit var myLogger: MyLogger
    private lateinit var navigator: Navigator
    private lateinit var backupDataUseCase: BackupDataUseCase
    private lateinit var recalculateTotalUseCase: RecalculateTotalUseCase
    private lateinit var restoreDataUseCase: RestoreDataUseCase
    private lateinit var myJsonReader: MyJsonReader
    private lateinit var myPreferencesRepository: MyPreferencesRepository
    private lateinit var transactionRepository: TransactionRepository
    private lateinit var alarmKit: AlarmKit

    private val dispatcherProvider = TestDispatcherProviderImpl(
        testDispatcher = UnconfinedTestDispatcher(),
    )

    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    internal fun setUp() {
        appVersionUtil = TestAppVersionUtil()
        myLogger = TestMyLogger()
        myJsonReader = TestMyJsonReader()
        myPreferencesRepository = TestMyPreferencesRepository()
        transactionRepository = FakeTransactionRepositoryImpl()
        alarmKit = FakeAlarmKitImpl()
        backupDataUseCase = BackupDataUseCase(
            dateTimeUtil = DateTimeUtilImpl(),
            dispatcherProvider = dispatcherProvider,
            getAllCategoriesUseCase = GetAllCategoriesUseCase(
                categoryRepository = FakeCategoryRepositoryImpl(),
            ),
            getAllAccountsUseCase = GetAllAccountsUseCase(
                accountRepository = FakeAccountRepositoryImpl(),
            ),
            getAllTransactionForValuesUseCase = GetAllTransactionForValuesUseCase(
                transactionForRepository = FakeTransactionForRepositoryImpl(),
            ),
            getAllTransactionsUseCase = GetAllTransactionsUseCase(
                transactionRepository = FakeTransactionRepositoryImpl(),
            ),
            myJsonWriter = FakeMyJsonWriterImpl(),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
        )
        recalculateTotalUseCase = RecalculateTotalUseCase(
            dispatcherProvider = dispatcherProvider,
            getAllAccountsUseCase = GetAllAccountsUseCase(
                accountRepository = FakeAccountRepositoryImpl(),
            ),
            getAllTransactionDataUseCase = GetAllTransactionDataUseCase(
                transactionRepository = FakeTransactionRepositoryImpl(),
            ),
            myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
            updateAccountsUseCase = UpdateAccountsUseCase(
                myPreferencesRepository = FakeMyPreferencesRepositoryImpl(),
                accountRepository = FakeAccountRepositoryImpl(),
            ),
        )
        restoreDataUseCase = RestoreDataUseCase(
            myJsonReader = myJsonReader,
            myLogger = myLogger,
            myPreferencesRepository = myPreferencesRepository,
            transactionRepository = transactionRepository,
        )
    }

    @After
    internal fun tearDown() {
    }

    @Test
    internal fun backupDataToDocumentTest() = runTest {
        val testUri = Uri.EMPTY
        navigator = FakeNavigatorImpl()
        initViewModel()

        turbineScope {
            settingsScreenViewModel.backupDataToDocument(
                uri = testUri,
            )

            val receiver = settingsScreenViewModel.navigator.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    */
/*
    @Test
    internal fun navigateToCategoriesScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToCategoriesScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.Categories,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    internal fun navigateToAccountsScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToAccountsScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.Accounts,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    internal fun navigateToTransactionForValuesScreenTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateToTransactionForValuesScreen()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.TransactionForValues,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    internal fun navigateUpTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            // settingsScreenViewModelImpl.navigateUp()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    internal fun restoreDataFromDocumentTest() = runTest {
        val testUri = Uri.EMPTY
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            settingsScreenViewModelImpl.restoreDataFromDocument(
                uri = testUri,
            )

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }

    @Test
    internal fun recalculateTotalTest() = runTest {
        navigationManager = FakeNavigationManagerImpl(
            coroutineScope = this,
        )
        initViewModel()

        turbineScope {
            settingsScreenViewModelImpl.recalculateTotal()

            val receiver = settingsScreenViewModelImpl.navigationManager.command.testIn(
                scope = backgroundScope,
            )

            Assert.assertEquals(
                MyNavigationDirections.NavigateUp,
                receiver.awaitItem(),
            )
            receiver.cancel()
        }
    }
    *//*


    private fun initViewModel() {
        settingsScreenViewModel = SettingsScreenViewModel(
            appVersionUtil = appVersionUtil,
            myPreferencesRepository = myPreferencesRepository,
            alarmKit = alarmKit,
            backupDataUseCase = backupDataUseCase,
            dispatcherProvider = dispatcherProvider,
            navigator = navigator,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }
}
*/
