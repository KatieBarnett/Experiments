package dev.katiebarnett.experiments.jcmodalbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandingContentSheet(modifier: Modifier = Modifier) {
    var showSheet by rememberSaveable { mutableStateOf(false) }
    var showConfirmChanges by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(modifier.fillMaxSize()) {
        Button(
            onClick = {
                showSheet = true
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Show sheet")
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showConfirmChanges = true },
            sheetState = sheetState,
            windowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier
        ) {
            BottomSheetContent(Modifier.)
        }
    }
}

@Composable
private fun ContentBox(backgroundColor: Color, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            content.invoke()
        }
    }
}

@Composable
private fun BottomSheetContent(modifier: Modifier = Modifier) {
    Column(modifier) {
        ContentBox(Color.Cyan, Modifier.height(80.dp)) {
            Text(text = "Sheet header")
        }
        ContentBox(Color.Magenta, Modifier.weight(1f)) {
            Text(text = "Sheet content")
        }
        ContentBox(Color.Yellow, Modifier.height(80.dp)) {
            Text(text = "Sheet footer")
        }
    }
}
