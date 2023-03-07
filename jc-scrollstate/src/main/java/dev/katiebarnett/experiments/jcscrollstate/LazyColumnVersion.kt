package dev.katiebarnett.experiments.jcscrollstate

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


private val scrollMap = mutableMapOf<Int, Int>()

private fun calculateScrollAmount(itemIndex: Int, itemScrolledAmount: Int): Int {
    scrollMap[itemIndex] = itemScrolledAmount
    scrollMap.keys.filter { it > itemIndex }.forEach {
        scrollMap.remove(it)
    }
    Log.d("calculateScrollAmount", "${scrollMap.values.toList()}")
    return scrollMap.values.sum()
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyColumnVersion() {
    val lazyListState = rememberLazyListState()
    val scrolledAmount = remember {
        derivedStateOf {
            calculateScrollAmount(lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Column {
                        Text(text = stringResource(R.string.title_lazy_column, scrolledAmount.value, lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset))
                        Text(text = stringResource(R.string.sub_title_lazy_column), style = MaterialTheme.typography.displaySmall)
                    }
                },
                colors = topAppBarColors()
            )
        },
        modifier = Modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(facts) {
                FactItem(it)
            }
        }
    }
}