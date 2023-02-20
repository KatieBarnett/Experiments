package dev.katiebarnett.experiments.jctopappbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.katiebarnett.experiments.core.theme.Dimen
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jctopappbar.components.AboutActionIcon
import dev.katiebarnett.experiments.jctopappbar.components.NavigationIcon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            ExperimentsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CollapsingTopAppBarNoStylingScreen(navController)
//                    CollapsingTopAppBarScreen(navController)
//                    SingleItemCollapsingTopAppBarScreen(navController)
//                    PinnedTopAppBarScreen(navController)
//                    FixedTopAppBarScreen(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBarNoStylingScreen(navController: NavController, modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val viewModel: MainViewModel = hiltViewModel()
    val items: List<Int> by viewModel.items.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "enterAlways") },
                navigationIcon = { NavigationIcon(navController = navController)},
                actions = { AboutActionIcon(navController) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor= MaterialTheme.colorScheme.onPrimary,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            items(items) { item ->
                Text(
                    text = stringResource(id = R.string.item_text, item),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(Dimen.spacing)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBarScreen(navController: NavController, modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.5 } }

    val topAppBarElementColor = if (isCollapsed.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
    val collapsedTextSize = 22
    val expandedTextSize = 28

    val topAppBarTextSize = (collapsedTextSize + (expandedTextSize - collapsedTextSize)*(1-scrollBehavior.state.collapsedFraction)).sp

    val viewModel: MainViewModel = hiltViewModel()
    val items: List<Int> by viewModel.items.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.title), fontSize = topAppBarTextSize) },
                navigationIcon = { NavigationIcon(navController = navController)},
                actions = { AboutActionIcon(navController) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor= topAppBarElementColor,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            items(items) { item ->
                Text(
                    text = stringResource(id = R.string.item_text, item),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(Dimen.spacing)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleItemCollapsingTopAppBarScreen(navController: NavController, modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.5 } }

    val topAppBarElementColor = if (isCollapsed.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }
    val collapsedTextSize = 22
    val expandedTextSize = 28

    val topAppBarTextSize = (collapsedTextSize + (expandedTextSize - collapsedTextSize)*(1-scrollBehavior.state.collapsedFraction)).sp

    val viewModel: MainViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.title), fontSize = topAppBarTextSize) },
                navigationIcon = { NavigationIcon(navController = navController)},
                actions = { AboutActionIcon(navController) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor= topAppBarElementColor,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(stringResource(id = R.string.empty_list_text))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinnedTopAppBarScreen(navController: NavController, modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val viewModel: MainViewModel = hiltViewModel()
    val items: List<Int> by viewModel.items.collectAsState(initial = emptyList())

    val listState = rememberLazyListState()
    val isScrolled = remember { derivedStateOf { scrollBehavior.state.contentOffset < -100f } }

    val topAppBarElementColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    val topAppBarContainerColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.primary
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.title)) },
                navigationIcon = { NavigationIcon(navController = navController)},
                actions = { AboutActionIcon(navController) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = topAppBarContainerColor,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface, // Ignored
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor= topAppBarElementColor,
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            items(items) { item ->
                Text(
                    text = stringResource(id = R.string.item_text, item),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(Dimen.spacing)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedTopAppBarScreen(navController: NavController, modifier: Modifier = Modifier) {

    val viewModel: MainViewModel = hiltViewModel()
    val items: List<Int> by viewModel.items.collectAsState(initial = emptyList())

    val listState = rememberLazyListState()
    val isScrolled = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

    val topAppBarElementColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onPrimary
    }

    val topAppBarContainerColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.primary
    }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(id = R.string.title)) },
                navigationIcon = { NavigationIcon(navController = navController)},
                actions = { AboutActionIcon(navController) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = topAppBarContainerColor,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface, // Ignored
                    navigationIconContentColor = topAppBarElementColor,
                    titleContentColor = topAppBarElementColor,
                    actionIconContentColor= topAppBarElementColor,
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            items(items) { item ->
                Text(
                    text = stringResource(id = R.string.item_text, item),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(Dimen.spacing)
                )
            }
        }
    }
}