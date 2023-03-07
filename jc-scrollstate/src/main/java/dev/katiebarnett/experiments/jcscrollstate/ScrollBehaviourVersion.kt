package dev.katiebarnett.experiments.jcscrollstate

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


const val TOTAL_HEADER_HEIGHT = 960

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollBehaviourVersion() {
    val lazyListState = rememberLazyListState()

    val localDensity = LocalDensity.current

    val scrolledAmount = remember { mutableStateOf(0f) }

    val headerHeightDp by remember {
        derivedStateOf {
            with(localDensity) {
                maxOf(0f, TOTAL_HEADER_HEIGHT - scrolledAmount.value).toDp()
            }
        }
    }

    val nestedScrollConnection = object: NestedScrollConnection{
        override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
            scrolledAmount.value = scrolledAmount.value + consumed.y
            return super.onPostScroll(consumed, available, source)
        }
    }
    Scaffold(
//        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
//                .nestedScroll(nestedScrollConnection)
            ,
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                    Spacer(Modifier.height(headerHeightDp))
                }
            items(facts) {
                FactItem(it)
            }
        }
    }
}
