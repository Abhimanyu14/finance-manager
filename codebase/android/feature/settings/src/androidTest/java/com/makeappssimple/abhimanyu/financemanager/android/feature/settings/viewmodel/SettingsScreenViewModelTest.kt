package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

/*
@Ignore("Fix Hilt")
@HiltAndroidTest
public class SettingsScreenViewModelTest {
    @get:Rule
    public val mainDispatcherRule: MainDispatcherRule = MainDispatcherRule()

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

    @Inject
    public lateinit var closeableCoroutineScope: CloseableCoroutineScope

    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    public fun setUp() {
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
    public fun tearDown() {
    }

    @Test
    public fun backupDataToDocumentTest(): TestResult = runTest {
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
    public fun navigateToCategoriesScreenTest(): TestResult = runTest {
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
    public fun navigateToAccountsScreenTest(): TestResult = runTest {
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
    public fun navigateToTransactionForValuesScreenTest(): TestResult = runTest {
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
    public fun navigateUpTest(): TestResult = runTest {
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
    public fun restoreDataFromDocumentTest(): TestResult = runTest {
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
    public fun recalculateTotalTest(): TestResult = runTest {
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
            closeableCoroutineScope = closeableCoroutineScope,
            dispatcherProvider = dispatcherProvider,
            navigator = navigator,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }
}
*/
