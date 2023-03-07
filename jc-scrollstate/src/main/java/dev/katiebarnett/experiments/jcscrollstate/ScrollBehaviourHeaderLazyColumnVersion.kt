package dev.katiebarnett.experiments.jcscrollstate

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollBehaviourHeaderLazyColumnVersion(modifier: Modifier = Modifier) {
    BoxWithConstraints {
        val localDensity = LocalDensity.current
        val totalHeaderHeight = remember {
            mutableStateOf(
                with(localDensity) {
                    (maxWidth / HEADER_IMAGE_ASPECT_RATIO).toPx()
                }
            )
        }
        var currentHeaderHeight by remember {
            mutableStateOf(totalHeaderHeight.value)
        }
        val currentCollapsedFraction by remember {
            derivedStateOf {
                if (totalHeaderHeight.value > 0) {
                    1 - (currentHeaderHeight / totalHeaderHeight.value)
                } else {
                    0f
                }
            }
        }
        // Smoothly animate the header height change
        val animatedCollapsedFraction by animateFloatAsState(
            targetValue = currentCollapsedFraction,
            label = "animatedCollapsedFraction"
        )

        Scaffold(
            topBar = {
                HeaderContent(
                    totalHeaderHeight = totalHeaderHeight.value,
                    percentCollapsed = animatedCollapsedFraction
                )
            },
            modifier = modifier
        ) { innerPadding ->
            ScrollingContent(
                onOffsetChanged = {
                    currentHeaderHeight = it
                },
                totalHeaderHeight = totalHeaderHeight.value,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HeaderContent(totalHeaderHeight: Float, percentCollapsed: Float, modifier: Modifier = Modifier) {

    val localDensity = LocalDensity.current
    val totalHeaderHeightDp = with(localDensity) {
        totalHeaderHeight.toDp()
    }
    val headerHeight = totalHeaderHeightDp * (1 - percentCollapsed)
    val contentOffset = -(totalHeaderHeightDp - headerHeight)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(totalHeaderHeightDp)
                .background(Color.Cyan)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painterResource(R.drawable.coral_reef_15796068239iq),
                contentDescription = null,
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(HEADER_IMAGE_ASPECT_RATIO)
                    .offset(y = contentOffset / 2)
            )
            Text(
                "c: $percentCollapsed h: $headerHeight o: $contentOffset",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

    }
}

@Composable
fun rememberNestedScrollConnection(onOffsetChanged: (Float) -> Unit, totalHeaderHeight: Float) = remember {
    // Set the initial height
    var currentHeight = totalHeaderHeight
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            // Update the current height by adding the vertical offset -> offset is negative when scrolling up the list
            // Ensure it is between 0 and the total height
            currentHeight = (currentHeight + available.y).coerceIn(minimumValue = 0f, maximumValue = totalHeaderHeight)

            return if (abs(currentHeight) == totalHeaderHeight || abs(currentHeight) == 0f) {
                // If the header is fully expanded or contracted, just use standard scroll
                super.onPreScroll(available, source)
            } else {
                // Update the header height, do not allow the content to actually scroll
                // (the change in header height will perform this 'scroll' instead)
                onOffsetChanged(currentHeight)
                available
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            // If scrolling with a fling, collapse or expand the header quickly in a smooth movement
            if (available.y < 0) {
                onOffsetChanged(0f)
            } else {
                onOffsetChanged(totalHeaderHeight)
            }
            return super.onPreFling(available)
        }
    }
}

@Composable
fun ScrollingContent(onOffsetChanged: (Float) -> Unit, totalHeaderHeight: Float, modifier: Modifier) {
    val nestedScrollState = rememberNestedScrollConnection(onOffsetChanged = onOffsetChanged, totalHeaderHeight = totalHeaderHeight)
    LaunchedEffect(Unit) {
        onOffsetChanged(totalHeaderHeight)
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollState)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(0.dp)) }
            items(facts) {
                FactItem(it)
            }
            item { Spacer(modifier = Modifier.height(0.dp)) }
        }
    }
}

data class HeaderPreviewData(
    val percentCollapsed: Float
)

class HeaderPreviewDataProvider : PreviewParameterProvider<HeaderPreviewData> {

    override val values = sequenceOf(
        HeaderPreviewData(0f),
        HeaderPreviewData(0.1f),
        HeaderPreviewData(0.3f),
        HeaderPreviewData(0.5f),
        HeaderPreviewData(0.7f),
        HeaderPreviewData(0.9f),
        HeaderPreviewData(1.0f),
    )
}

@Preview(widthDp = 800, heightDp = 300)
@Composable
fun HeaderContentPreview(
    @PreviewParameter(HeaderPreviewDataProvider::class) data: HeaderPreviewData
) {
    Box(
        Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        HeaderContent(960f, data.percentCollapsed)
    }
}

