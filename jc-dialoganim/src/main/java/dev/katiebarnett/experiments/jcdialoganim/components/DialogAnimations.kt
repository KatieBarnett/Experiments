package dev.katiebarnett.experiments.jcdialoganim.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal const val ANIMATION_TIME = 1000L
internal const val DIALOG_BUILD_TIME = 300L 

// Inspired by https://medium.com/tech-takeaways/ios-like-modal-view-dialog-animation-in-jetpack-compose-fac5778969af

@Composable
internal fun AnimatedModalBottomSheetTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
            initialOffsetY = { fullHeight -> fullHeight }
        ),
        exit = slideOutVertically(
            animationSpec = tween(ANIMATION_TIME.toInt()),
            targetOffsetY = { fullHeight -> fullHeight }
        ),
        content = content
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AnimatedScaleInTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(
            animationSpec = tween(ANIMATION_TIME.toInt())
        ),
        exit = scaleOut(
            animationSpec = tween(ANIMATION_TIME.toInt())
        ),
        content = content
    )
}

@Composable
fun ModalTransitionDialog(
    onDismissRequest: () -> Unit,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (ModalTransitionDialogHelper) -> Unit
) {
    val onCloseSharedFlow: MutableSharedFlow<Any> = remember { MutableSharedFlow() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateContentBackTrigger = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(DIALOG_BUILD_TIME)
            animateContentBackTrigger.value = true
        }
        launch {
            onCloseSharedFlow.asSharedFlow().collectLatest { 
                startDismissWithExitAnimation(animateContentBackTrigger, onDismissRequest) 
            }
        }
    }

    Dialog(
        onDismissRequest = { 
            coroutineScope.launch { 
                startDismissWithExitAnimation(animateContentBackTrigger, onDismissRequest) 
            } 
        }
    ) {
        Box(contentAlignment = contentAlignment, 
            modifier = Modifier.fillMaxSize() 
        ) { 
            AnimatedScaleInTransition(visible = animateContentBackTrigger.value) {
                content(ModalTransitionDialogHelper(coroutineScope, onCloseSharedFlow))
            }
        }
    }
}


class ModalTransitionDialogHelper(
    private val coroutineScope: CoroutineScope, 
    private val onCloseFlow: MutableSharedFlow<Any>) {
    fun triggerAnimatedClose() {
        coroutineScope.launch {
            onCloseFlow.emit(Any())
        }
    }
}

suspend fun startDismissWithExitAnimation(
    animateContentBackTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateContentBackTrigger.value = false
    delay(ANIMATION_TIME)
    onDismissRequest()
}