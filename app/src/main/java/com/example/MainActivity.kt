package com.example

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.auth.attemptAutoSignIn
import com.example.data.StudentDataRepository
import com.example.model.UiState
import com.example.ui.components.CosmicBackground
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.FavoritesNotesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LawsBankScreen
import com.example.ui.screens.QuizzesScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.TipsScreen
import com.example.ui.theme.CalmGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricBlueContainer
import com.example.ui.theme.MidnightNavy
import com.example.ui.theme.RafidPhysicsTheme
import com.example.viewmodel.QuizViewModel
import com.example.viewmodel.StudentSyncViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

const val ROUTE_HOME = "home"
const val ROUTE_LAWS = "laws"
const val ROUTE_QUIZZES = "quizzes"
const val ROUTE_SAVED = "saved"
const val ROUTE_CHAPTER = "chapter/{chapterNumber}"
const val ROUTE_TIPS = "tips"
const val ROUTE_ABOUT = "about"

private data class BottomNavItem(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val bottomNavItems = listOf(
    BottomNavItem(
        route = ROUTE_HOME,
        labelRes = R.string.nav_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        testTag = "nav_home"
    ),
    BottomNavItem(
        route = ROUTE_LAWS,
        labelRes = R.string.nav_laws,
        selectedIcon = Icons.Filled.Calculate,
        unselectedIcon = Icons.Outlined.Calculate,
        testTag = "nav_laws"
    ),
    BottomNavItem(
        route = ROUTE_QUIZZES,
        labelRes = R.string.nav_quizzes,
        selectedIcon = Icons.Filled.Quiz,
        unselectedIcon = Icons.Outlined.Quiz,
        testTag = "nav_quizzes"
    ),
    BottomNavItem(
        route = ROUTE_SAVED,
        labelRes = R.string.nav_saved,
        selectedIcon = Icons.Filled.Bookmark,
        unselectedIcon = Icons.Outlined.BookmarkBorder,
        testTag = "nav_saved"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                RafidPhysicsTheme {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember(context) { CredentialManager.create(context) }

    var showSplash by remember { mutableStateOf(true) }
    var currentUser by remember { mutableStateOf(Firebase.auth.currentUser) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            currentUser = auth.currentUser
        }
        Firebase.auth.addAuthStateListener(listener)
        onDispose { Firebase.auth.removeAuthStateListener(listener) }
    }

    LaunchedEffect(Unit) {
        attemptAutoSignIn(
            context = context,
            credentialManager = credentialManager,
            onAuthSuccess = { currentUser = Firebase.auth.currentUser },
            onUnauthenticated = { currentUser = Firebase.auth.currentUser },
            scope = scope
        )
    }

    if (showSplash) {
        SplashScreen(onFinished = { showSplash = false })
        return
    }

    val user = currentUser
    if (user == null) {
        AuthScreen(onAuthSuccess = { currentUser = Firebase.auth.currentUser })
    } else {
        key(user.uid) {
            AuthenticatedAppShell(currentUserId = user.uid)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticatedAppShell(currentUserId: String) {
    val syncViewModel: StudentSyncViewModel = viewModel(
        key = currentUserId,
        factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY]) {
                    "APPLICATION_KEY missing from CreationExtras"
                }
                val databaseId = app.getString(R.string.firestore_database_id)
                val db = FirebaseFirestore.getInstance(databaseId)
                StudentSyncViewModel(StudentDataRepository(db), currentUserId)
            }
        }
    )
    val quizViewModel: QuizViewModel = viewModel(key = "quiz_$currentUserId")

    val favoritesState by syncViewModel.favoritesState.collectAsStateWithLifecycle()
    val notesState by syncViewModel.notesState.collectAsStateWithLifecycle()
    val quizResultsState by syncViewModel.quizResultsState.collectAsStateWithLifecycle()

    val favoriteIds = remember(favoritesState) {
        when (val state = favoritesState) {
            is UiState.Success -> state.data.map { it.itemId.ifEmpty { it.id } }.toSet()
            else -> emptySet()
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(syncViewModel) {
        syncViewModel.feedbackMessages.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showChromeNavigation = currentRoute in bottomNavItems.map { it.route }

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isExpanded = maxWidth >= 600.dp

        CosmicBackground(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (isExpanded && showChromeNavigation) {
                    NavigationRail(
                        containerColor = MidnightNavy.copy(alpha = 0.92f),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        bottomNavItems.forEach { item ->
                            val selected = currentRoute == item.route
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTopLevel(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = stringResource(item.labelRes)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(item.labelRes),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = ElectricBlue,
                                    selectedTextColor = ElectricBlue,
                                    indicatorColor = ElectricBlueContainer
                                ),
                                modifier = Modifier.testTag(item.testTag)
                            )
                        }
                    }
                }

                Scaffold(
                    containerColor = androidx.compose.ui.graphics.Color.Transparent,
                    contentWindowInsets = WindowInsets.safeDrawing,
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = {
                        if (showChromeNavigation) {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = stringResource(R.string.professor_full_title),
                                        style = MaterialTheme.typography.titleLarge,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                actions = {
                                    IconButton(
                                        onClick = { navController.navigate(ROUTE_TIPS) },
                                        modifier = Modifier.testTag("topbar_tips_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Lightbulb,
                                            contentDescription = "نصائح الامتحان الوزاري",
                                            tint = CalmGold
                                        )
                                    }
                                    IconButton(
                                        onClick = { navController.navigate(ROUTE_ABOUT) },
                                        modifier = Modifier.testTag("topbar_about_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "حول الأستاذ",
                                            tint = ElectricBlue
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MidnightNavy.copy(alpha = 0.85f),
                                    titleContentColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                        }
                    },
                    bottomBar = {
                        if (!isExpanded && showChromeNavigation) {
                            NavigationBar(
                                containerColor = MidnightNavy.copy(alpha = 0.96f),
                                tonalElevation = 4.dp
                            ) {
                                bottomNavItems.forEach { item ->
                                    val selected = currentRoute == item.route
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = { navigateTopLevel(item.route) },
                                        icon = {
                                            Icon(
                                                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                                contentDescription = stringResource(item.labelRes)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = stringResource(item.labelRes),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = ElectricBlue,
                                            selectedTextColor = ElectricBlue,
                                            indicatorColor = ElectricBlueContainer,
                                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        modifier = Modifier.testTag(item.testTag)
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                favoriteIds = favoriteIds,
                                onToggleFavorite = syncViewModel::toggleFavorite,
                                onOpenChapter = { chapterNum ->
                                    navController.navigate("chapter/$chapterNum")
                                },
                                onOpenTips = { navController.navigate(ROUTE_TIPS) },
                                onOpenAbout = { navController.navigate(ROUTE_ABOUT) }
                            )
                        }

                        composable(ROUTE_LAWS) {
                            LawsBankScreen(
                                favoriteIds = favoriteIds,
                                onToggleFavorite = syncViewModel::toggleFavorite
                            )
                        }

                        composable(ROUTE_QUIZZES) {
                            QuizzesScreen(
                                quizViewModel = quizViewModel,
                                quizResultsState = quizResultsState,
                                onSaveQuizResult = syncViewModel::recordQuizScore
                            )
                        }

                        composable(ROUTE_SAVED) {
                            FavoritesNotesScreen(
                                favoritesState = favoritesState,
                                notesState = notesState,
                                onRemoveFavorite = syncViewModel::removeFavorite,
                                onSaveNote = syncViewModel::saveNote,
                                onDeleteNote = syncViewModel::deleteNote
                            )
                        }

                        composable(
                            route = ROUTE_CHAPTER,
                            arguments = listOf(navArgument("chapterNumber") { type = NavType.IntType })
                        ) { entry ->
                            val chapterNumber = entry.arguments?.getInt("chapterNumber") ?: 1
                            ChapterDetailScreen(
                                chapterNumber = chapterNumber,
                                favoriteIds = favoriteIds,
                                onToggleFavorite = syncViewModel::toggleFavorite,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(ROUTE_TIPS) {
                            TipsScreen(onBack = { navController.popBackStack() })
                        }

                        composable(ROUTE_ABOUT) {
                            AboutScreen(
                                onBack = { navController.popBackStack() },
                                onShowMessage = { msg ->
                                    scope.launch { snackbarHostState.showSnackbar(msg) }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
