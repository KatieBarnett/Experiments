package dev.katiebarnett.experiments.jcscrollstate

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.withContext


private val scrollMap = mutableMapOf<Int, Pair<Int, Int>>()

private fun calculateScrollAmount(itemIndex: Int, itemScrolledAmount: Int): Int {
    scrollMap[itemIndex] = Pair(itemScrolledAmount, scrollMap[itemIndex]?.second ?: 0)
    scrollMap.filter { it.key < itemIndex }.forEach {
        // Update the final scroll value
        scrollMap[it.key] = Pair(it.value.second, it.value.second)
    }
    scrollMap.filter { it.key > itemIndex }.forEach {
        scrollMap[it.key] = Pair(0, it.value.second)
    }
    Log.d("calculateScrollAmount", "item: $itemIndex $itemScrolledAmount ${scrollMap.values.toList()}")
    return scrollMap.values.sumOf { it.first }
}

const val HEADER_IMAGE_ASPECT_RATIO = 1920 / 1280f

const val HEADER_HEIGHT= 960

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingHeaderVersion() {
    val lazyListState = rememberLazyListState()
    val scrolledAmountPx by remember {
        derivedStateOf {
            calculateScrollAmount(lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset)
        }
    }


    val localDensity = LocalDensity.current

    val headerHeightNewDp by remember {
        derivedStateOf {
            with(localDensity) {
                if (lazyListState.firstVisibleItemIndex == 0) {
                    (HEADER_HEIGHT - lazyListState.firstVisibleItemScrollOffset).toDp()
                } else {
                    0.dp
                }
            }
        }
    }
    val topAppBarHeightPx = remember { mutableStateOf(960) }
    val collapsedFraction by remember {
        derivedStateOf {
            if (topAppBarHeightPx.value == 0) {
                0f
            } else if (scrolledAmountPx > topAppBarHeightPx.value) {
                1f
            } else {
                scrolledAmountPx / topAppBarHeightPx.value.toFloat()
            }
        }
    }


//    val headerOffsetPx by remember {
//        derivedStateOf {
//            if (scrolledAmountPx > topAppBarHeightPx) {
//                topAppBarHeightPx
//            } else {
//                scrolledAmountPx
//            }
//        }
//    }
//
//
//    val headerHeightPx by remember {
//        derivedStateOf {
//            topAppBarHeightPx - headerOffsetPx
//        }
//    }
//
//    val headerHeightDp by remember {
//        derivedStateOf {
//            with(localDensity) { (topAppBarHeightPx - headerOffsetPx).toDp() }
//        }
//    }
//
//    val headerHeightDp by remember {
//        derivedStateOf {
//            with(localDensity) {
//                topAppBarHeightPx.value.toDp() * (1 - collapsedFraction)
//            }
//        }
//    }

    Scaffold(

//            TopAppBar(
//                title = {
//
//                    Column {
//                        Text(text = stringResource(R.string.title_lazy_column, scrolledAmount.value, lazyListState.firstVisibleItemIndex, lazyListState.firstVisibleItemScrollOffset))
//                        Text(text = stringResource(R.string.sub_title_lazy_column), style = MaterialTheme.typography.displaySmall)
//                    }
//                },
//                colors = topAppBarColors()
//            )
//        },
        modifier = Modifier
    ) { contentPadding ->

//        val topContentPadding by remember {
//            derivedStateOf {
//                contentPadding.calculateTopPadding() * (1 - collapsedFraction)
////                maxOf(0.dp, contentPadding.calculateTopPadding() - with(localDensity) { headerOffsetPx.toDp() })
//            }
//        }

        Log.d(
        "calculateScrollAmount",
        "scrolledAmountPx: ${scrolledAmountPx} " +
//            "topAppBarHeightPx: ${topAppBarHeightPx} " +
                "headerHeightNewDp: ${headerHeightNewDp} "
//                "collapsedFraction: ${collapsedFraction} "
        )

        Box(
            Modifier
                .padding(top = with(localDensity)  {
                    (topAppBarHeightPx.value * (1-collapsedFraction)).toDp()
                },
                    start = contentPadding.calculateStartPadding(Ltr),
                    end = contentPadding.calculateEndPadding(Ltr),
                    bottom = contentPadding.calculateBottomPadding()
                )
                .background(Color.Red)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue),
                state = lazyListState,
//                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
//                item {
//                    Spacer(Modifier.height(headerHeightNewDp))
//                }
                itemsIndexed(facts) {index, fact ->
                    FactItem(fact,
                        Modifier.onGloballyPositioned {
                            if (!scrollMap.containsKey(index) || scrollMap[index]?.second == 0) {
                                scrollMap[index] = Pair(0, it.size.height)
                                Log.d(
                                    "calculateScrollAmount",
                                    "$index :: ${fact.title.substring(0..8)} height: ${it.size.height} "
                                )
                            }
                    }
                    )
                }

            }

//            Box(
//                Modifier.fillMaxWidth()
//                    .background(Color.Gray)
//                    .height(headerHeightNewDp)
//                    .align(Alignment.TopCenter)) {
//
//                AsyncImage(
//                    model = R.drawable.coral_reef_15796068239iq,
//                    contentDescription = null,
//                    contentScale = ContentScale.FillBounds,
//                    modifier = Modifier
//                        .fillMaxSize()
//
//                        .aspectRatio(HEADER_IMAGE_ASPECT_RATIO)
//                                                .onGloballyPositioned { coordinates ->
//                            if (topAppBarHeightPx.value == 0) {
//                                topAppBarHeightPx.value = coordinates.size.height
//                            }
//                        }
//                        .offset(y = -headerHeightDp * collapsedFraction)
//                )
//            }
        }

    }
}
