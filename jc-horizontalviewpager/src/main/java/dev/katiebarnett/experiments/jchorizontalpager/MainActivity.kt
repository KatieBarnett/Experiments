package dev.katiebarnett.experiments.jchorizontalpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {

    data class BoxObject(
        val height: Dp,
        val backgroundColor: Color
    )

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class, ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ExperimentsTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.app_name)) },
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

                    val pageHeights = listOf(
                        BoxObject(1600.dp, Color.Red),
                        BoxObject(1700.dp, Color.Green),
                        BoxObject(1000.dp, Color.Blue),
                        BoxObject(1400.dp, Color.Cyan),
                    )

                    val listState = rememberScrollState()
                    val pagerState = rememberPagerState()

                    HorizontalPager(
                        count = pageHeights.size,
                        state = pagerState
                    ) { page ->
                        pageHeights[page].let {
                            LazyColumn(Modifier//.verticalScroll(listState)
                                .padding(innerPadding)) {
                                item{
                                Surface(
                                    color = it.backgroundColor,
                                    border = BorderStroke(width = 10.dp,
                                    color = Color.Magenta)
                                ) {
                                    Box(
                                        Modifier
                                            .height(it.height)
                                            .fillMaxWidth()
                                    ) {
                                    }
                                }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}