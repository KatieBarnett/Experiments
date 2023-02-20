package dev.katiebarnett.experiments.jcrefresh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import dev.katiebarnett.experiments.jcrefresh.model.Fox

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ExperimentsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.title)) },
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                actionIconContentColor= MaterialTheme.colorScheme.onPrimary,
                            )
                        )
                    },
                    modifier = Modifier
                ) { innerPadding ->
                    val viewModel: MainViewModel = hiltViewModel()
                    val foxData: Fox? by viewModel.item.collectAsStateWithLifecycle()
                    val foxDataItems: List<Fox> by viewModel.items.collectAsStateWithLifecycle(listOf())
                    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
                    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.refresh() })

                    // Single item
                    Box(
                        Modifier.padding(innerPadding)
                            .pullRefresh(pullRefreshState)
                            .verticalScroll(rememberScrollState())
                    ) {
                       foxData?.image?.let {
                            AsyncImage(
                                model = it,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        PullRefreshIndicator(
                            refreshing = isRefreshing,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }


                    // Multiple items
//                    Box(
//                        Modifier
//                            .padding(innerPadding)
//                            .pullRefresh(pullRefreshState)
//                    ) {
//                        LazyColumn {
//                            items(foxDataItems) { fox ->
//                                AsyncImage(
//                                    model = fox.image,
//                                    contentDescription = null,
//                                    contentScale = ContentScale.Fit,
//                                    modifier = Modifier.fillMaxWidth().aspectRatio(960/640f)
//                                )
//                            }
//                        }
//                        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
//                    }
                }
            }
        }
    }
}