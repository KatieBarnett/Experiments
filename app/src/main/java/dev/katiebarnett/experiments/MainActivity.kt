package dev.katiebarnett.experiments

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
// import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExperimentsTheme {
                Box(Modifier.safeDrawingPadding()) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Content()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
        item {
            Text("Demo app")
        }
        item {
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)
////                        val dateFormatter = remember { DatePickerDefaults.dateFormatter(locale = defaultLocale()) }
//
            DatePicker(
                state = datePickerState,
//                            dateFormatter = dateFormatter,
                modifier = Modifier,
                showModeToggle = true
            )
        }
//        item {
//            DatePickerModal
//        }
//        item {
//            Dropdown()
//        }
//        item {
//            EditableDropdown()
//        }
//        item {
//            EditableDropdownMultiSelect()
//        }
    }
}

// https://github.com/androidx/androidx/blob/530861e1e0d696daf10f0d8c565dd6af68ab9577/compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/ExposedDropdownMenuSamples.kt

val options = listOf(
    "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Argentina",
    "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
    "Belarus", "Belgium", "Belize", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
    "Brazil", "Bulgaria", "Burundi", "Cambodia", "Cameroon", "Canada",
    "Chile", "China", "Colombia", "Costa Rica", "Côte d'Ivoire", "Croatia",
    "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",
    "Denmark", "Djibouti", "Dominica", " Dominican Republic", "Ecuador", "Egypt",
    "El Salvador", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland",
    "France", "Gabon", "Gambia", "Germany", "Ghana", "Greece",
    "Guatemala", "Guinea", "Guyana", "Haiti", "Honduras", "Hungary",
    "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownItem(option: AnnotatedString, selected: Boolean, onClick: () -> Unit) {
    DropdownMenuItem(
        text = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (selected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "",
                        modifier = Modifier.width(30.dp)
                    )
                } else {
                    Spacer(Modifier.width(30.dp))
                }
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "LABEL",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.background(Color.LightGray).padding(4.dp)
                )
            }
        },
        onClick = onClick,
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown() {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf<String?>(null) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier.fillMaxWidth().menuAnchor(/*ExposedDropdownMenuAnchorType.PrimaryNotEditable*/),
            value = text.orEmpty(),
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text("Non-editable dropdown") },
            placeholder = { Text("placeholder") },
            supportingText = { Text("supporting text") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(
                focusable = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true).shadow(elevation = 2.dp)
        ) {
            options.forEach { option ->
                DropDownItem(
                    option = AnnotatedString(option),
                    selected = option == text,
                    onClick = {
                        text = if (option == text) {
                            null
                        } else {
                            option
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableDropdown() {
    var text by remember { mutableStateOf(TextFieldValue()) }

    // The text that the user inputs into the text field can be used to filter the options.
    // This sample uses string subsequence matching.
    val filteredOptions = options.filteredBy(text.text)

    val (allowExpanded, setExpanded) = remember { mutableStateOf(false) }
    val expanded = allowExpanded && filteredOptions.isNotEmpty()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = setExpanded,
    ) {
        OutlinedTextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. An editable text field has
            // the anchor type `PrimaryEditable`.
            modifier = Modifier.fillMaxWidth().menuAnchor(/*ExposedDropdownMenuAnchorType.PrimaryEditable*/),
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            label = { Text("Editable dropdown") },
            placeholder = { Text("placeholder") },
            supportingText = { Text("supporting text") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                    // If the text field is editable, it is recommended to make the
                    // trailing icon a `menuAnchor` of type `SecondaryEditable`. This
                    // provides a better experience for certain accessibility services
                    // to choose a menu option without typing.
//                    modifier = Modifier.menuAnchor(/*ExposedDropdownMenuAnchorType.SecondaryEditable*/),
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            properties = PopupProperties(
                focusable = true,
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            modifier = Modifier.exposedDropdownSize(matchTextFieldWidth = true)
        ) {
            filteredOptions.forEach { option ->
                DropDownItem(
                    option = option,
                    selected = option.text == text.text,
                    onClick = {
                        text = if (option.text == text.text) {
                            TextFieldValue()
                        } else {
                            TextFieldValue(
                                text = option.text,
                                selection = TextRange(option.text.length),
                            )
                        }
                        setExpanded(false)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditableDropdownMultiSelect() {
    var text by remember { mutableStateOf(TextFieldValue()) }
    val selectedValues = remember {
        mutableStateListOf<String>()
    }

    // The text that the user inputs into the text field can be used to filter the options.
    // This sample uses string subsequence matching.
    val filteredOptions = options.filteredBy(text.text)

    val (allowExpanded, setExpanded) = remember { mutableStateOf(false) }
    val expanded = allowExpanded && filteredOptions.isNotEmpty()

    Column {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            selectedValues.forEach {
                InputChip(
                    onClick = {
                        selectedValues.remove(it)
                    },
                    label = { Text(it) },
                    selected = false,
                    trailingIcon = {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Localized description",
                            Modifier.size(InputChipDefaults.AvatarSize)
                        )
                    },
                )
            }
        }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = setExpanded,
        ) {
//            OutlinedTextField(
//                // The `menuAnchor` modifier must be passed to the text field to handle
//                // expanding/collapsing the menu on click. An editable text field has
//                // the anchor type `PrimaryEditable`.
//                modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryEditable),
//                value = text,
//                onValueChange = { text = it },
//                singleLine = true,
//                label = { Text("Editable multi select dropdown") },
//                placeholder = { Text("placeholder") },
//                supportingText = { Text("supporting text") },
//                trailingIcon = {
//                    ExposedDropdownMenuDefaults.TrailingIcon(
//                        expanded = expanded,
//                        // If the text field is editable, it is recommended to make the
//                        // trailing icon a `menuAnchor` of type `SecondaryEditable`. This
//                        // provides a better experience for certain accessibility services
//                        // to choose a menu option without typing.
////                    modifier = Modifier.menuAnchor(MenuAnchorType.SecondaryEditable),
//                    )
//                },
//                colors = ExposedDropdownMenuDefaults.textFieldColors(),
//            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { setExpanded(false) },
            ) {
                filteredOptions.forEach { option ->
                    DropDownItem(
                        option = option,
                        selected = selectedValues.contains(option.text),
                        onClick = {
                            if (selectedValues.contains(option.text)) {
                                selectedValues.remove(option.text)
                            } else {
                                selectedValues.add(option.text)
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Returns the element of [this] list that contain [text] as a subsequence, with the subsequence
 * underlined as an [AnnotatedString].
 */
private fun List<String>.filteredBy(text: String): List<AnnotatedString> {
    fun underlineSubsequence(needle: String, haystack: String): AnnotatedString? {
        return buildAnnotatedString {
            var i = 0
            for (char in needle) {
                val start = i
                haystack.indexOf(char, startIndex = i, ignoreCase = true).let {
                    if (it < 0) return null else i = it
                }
                append(haystack.substring(start, i))
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(haystack[i])
                }
                i += 1
            }
            append(haystack.substring(i, haystack.length))
        }
    }
    return this.mapNotNull { option -> underlineSubsequence(text, option) }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExperimentsTheme {
        Content()
    }
}
