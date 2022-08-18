package dev.katiebarnett.experiments.jcdialoganim.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RegularDialog(buttonAction: () -> Unit,
                   onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) { 
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("This is a regular dialog")
                Button(onClick = {
                    buttonAction.invoke()
                    onDismissRequest.invoke()
                    
                }) {
                    Text("Close Button")
                }
            }
        }
    }
}

@Composable
fun AnimatedDialog(buttonAction: () -> Unit,
                   onDismissRequest: () -> Unit,
                   animatedTransitionDialogHelper: AnimatedTransitionDialogHelper
) {
    AnimatedTransitionDialog(onDismissRequest = onDismissRequest) { 
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("This is an animated dialog")
                Button(onClick = {
                    buttonAction.invoke()
                    animatedTransitionDialogHelper::triggerAnimatedDismiss.invoke()
                }) {
                    Text("Close Button")
                }
            }
        }
    }
}

@Composable
fun AnimatedEntryDialog(buttonAction: () -> Unit,
                   onDismissRequest: () -> Unit
) {
    AnimatedTransitionDialogEntryOnly(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("This is an animated dialog with entry animation")
                Button(onClick = {
                    buttonAction.invoke()
                    onDismissRequest.invoke()
                }) {
                    Text("Close Button")
                }
            }
        }
    }
}