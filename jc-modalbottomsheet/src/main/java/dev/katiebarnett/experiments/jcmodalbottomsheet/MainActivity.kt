package dev.katiebarnett.experiments.jcmodalbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExperimentsTheme {
                val coroutineScope = rememberCoroutineScope()
                var items by rememberSaveable { mutableStateOf(listOf<String>()) }
                var showAddSheet by rememberSaveable { mutableStateOf(false) }
                var showConfirmChanges by rememberSaveable { mutableStateOf(false) }
                val addSheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true,
                    confirmValueChange = {
                        if (it == SheetValue.Hidden) {
                            showConfirmChanges = true
                            false
                        } else {
                            // We're expanding the sheet so we always return true
                            true
                        }
                    }
                )
                Scaffold(
                    topBar = {
                        Box(modifier = Modifier) {
                            Image(
                                painter = painterResource(id = R.drawable.fruit),
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(0.5f)
                            )
                            Text(
                                text = stringResource(id = R.string.app_bar),
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(8.dp)
                            )
                        }
                    },
                    modifier = Modifier
                ) { innerPadding ->
                    ShoppingList(
                        items = items,
                        onAddItemClick = { showAddSheet = true },
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )

                    if (showAddSheet) {
                        ModalBottomSheet(
                            onDismissRequest = { showConfirmChanges = true },
                            sheetState = addSheetState,
                            modifier = Modifier
                        ) {
                            BottomSheetContent(
                                onSaveClick = {
                                    items = items.plus(it)
                                    coroutineScope.launch {
                                        addSheetState.hide()
                                    }.invokeOnCompletion {
                                        if (!addSheetState.isVisible) {
                                            showAddSheet = false
                                        }
                                    }
                                }
                            )
                        }
                    }

                    if (showConfirmChanges) {
                        AlertDialog(
                            onDismissRequest = { showConfirmChanges = false },
                            title = { Text(stringResource(R.string.confirm_changes_title)) },
                            text = { Text(stringResource(R.string.confirm_changes_text)) },
                            confirmButton = {
                                TextButton(onClick = {
                                    coroutineScope.launch {
                                        addSheetState.hide()
                                    }.invokeOnCompletion {
                                        if (!addSheetState.isVisible) {
                                            showAddSheet = false
                                        }
                                    }
                                    showConfirmChanges = false
                                }) {
                                    Text(stringResource(R.string.confirm_changes_confirm))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showConfirmChanges = false
                                }) {
                                    Text(stringResource(R.string.confirm_changes_dismiss))
                                }
                            },
                            properties = DialogProperties(
                                dismissOnBackPress = false,
                                dismissOnClickOutside = false
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    onSaveClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var firstTextChange by remember { mutableStateOf(false) }
    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var item by remember { mutableStateOf<String?>(null) }
        OutlinedTextField(
            value = item.orEmpty(),
            onValueChange = {
                firstTextChange = true
                item = it
            },
            label = { Text(stringResource(R.string.add_new_hint)) },
            isError = item.isNullOrBlank() && firstTextChange,
            supportingText = {
                if (item.isNullOrBlank() && firstTextChange) {
                    Text(
                        text = stringResource(R.string.validation_message_item),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
        )
        Button(
            enabled = !item.isNullOrBlank(),
            onClick = {
                item?.let {
                    onSaveClick.invoke(it)
                }
            },

            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.add_new_save))
        }
    }
}

@Composable
fun ShoppingList(
    items: List<String>,
    onAddItemClick: () -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) {
            Text(text = it, Modifier)
        }
        item {
            Button(
                onClick = { onAddItemClick.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.add_new))
            }
        }
    }
}
