package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.viewmodel

/*
@HiltAndroidTest
class SettingsScreenViewModelTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val testCoroutineScope = TestScope(
        context = dispatcherProvider.io + Job(),
    )

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private var appVersionUtil: AppVersionUtil = AppVersionUtilImpl(
        context = context,
    )
    private var buildConfigUtil: BuildConfigUtil = BuildConfigUtilImpl()
    private var logger: Logger = LoggerImpl(
        buildConfigUtil = buildConfigUtil,
    )
    private val navigationManager: NavigationManager = NavigationManagerImpl(
        coroutineScope = testCoroutineScope,
    )
    private val backupDataUseCase: BackupDataUseCase = mock()
    private val recalculateTotalUseCase: RecalculateTotalUseCase = mock()
    private val restoreDataUseCase: RestoreDataUseCase = mock()
    private lateinit var settingsScreenViewModel: SettingsScreenViewModel

    @Before
    fun setUp() {
        settingsScreenViewModel = SettingsScreenViewModelImpl(
            logger = logger,
            navigationManager = navigationManager,
            appVersionUtil = appVersionUtil,
            backupDataUseCase = backupDataUseCase,
            dispatcherProvider = dispatcherProvider,
            recalculateTotalUseCase = recalculateTotalUseCase,
            restoreDataUseCase = restoreDataUseCase,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun backupDataToDocument() = runTest {
        val uri: Uri = mock()

        settingsScreenViewModel.backupDataToDocument(
            uri = uri,
        )

        verify(
            mock = backupDataUseCase,
        ).invoke(
            uri = uri,
        )
    }

    @Test
    fun restoreDataFromDocument() = runTest {
        val uri: Uri = mock()

        settingsScreenViewModel.restoreDataFromDocument(
            uri = uri,
        )

        verify(
            mock = restoreDataUseCase,
        ).invoke(
            uri = uri,
        )
    }

    @Test
    fun recalculateTotal() = runTest {
        settingsScreenViewModel.recalculateTotal()

        verify(
            mock = recalculateTotalUseCase,
        ).invoke()
    }
}
*/
