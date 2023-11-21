package dev.katiebarnett.experiments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme

// Standard preview parameters
class TextConfigProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "Primary text", "Secondary text"
    )
}

@Preview
@Composable
fun TextComponentPreview(
    @PreviewParameter(TextConfigProvider::class) data: String
) {
    MaterialTheme {
        Text(data)
    }
}

// Composable value as data
data class TextConfig(
    val color: Color,
    val text: String
)

fun interface TextData {
    @Composable
    fun value(): TextConfig
}

private val primary = TextData {
    TextConfig(color = MaterialTheme.colorScheme.primary, text = "Primary text")
}

private val secondary = TextData {
    TextConfig(color = MaterialTheme.colorScheme.secondary, text = "Secondary text")
}

//class TextWithColorConfigProvider : PreviewParameterProvider<TextConfig> {
//    override val values: Sequence<TextConfig> = sequenceOf(
//        TextConfig(color = Color.Red, text = "Primary text"),
//        TextConfig(color = Color.Blue, text = "Secondary text")
//    )
//}

class TextWithColorConfigProvider : PreviewParameterProvider<TextData> {
    override val values: Sequence<TextData> = sequenceOf(
        primary, secondary
    )
}

//@Preview
//@Composable
//fun TextColoredComponentPreview(
//    @PreviewParameter(TextWithColorConfigProvider::class) data: TextConfig
//) {
//    MaterialTheme {
//        Text(data.text, color = data.color)
//    }
//}

@Preview
@Composable
fun TextColoredComponentPreviewDataCreatedInside() {
    val previewData = sequenceOf(
        TextConfig(color = MaterialTheme.colorScheme.primary, text = "Primary text"),
        TextConfig(color = MaterialTheme.colorScheme.secondary, text = "Secondary text")
    )
    MaterialTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            previewData.forEach {data ->
                Text(data.text, color = data.color)
            }
        }
    }
}

@Preview
@Composable
fun TextColoredComponentPreview(
    @PreviewParameter(TextWithColorConfigProvider::class) data: TextData
) {
    MaterialTheme {
        Text(data.value().text, color = data.value().color)
    }
}

// Composable as data
data class ButtonConfig(
    val buttonContent: @Composable RowScope.(Modifier) -> Unit,
    val background: Color,
    val contentColor: Color,
)

fun interface ButtonData {
    @Composable
    fun value(): ButtonConfig
}

private val primaryWithIcon = ButtonData {
    ButtonConfig(buttonContent = {
        ButtonContentWithIcon(text = "Primary button with icon")
    }, background = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
}

private val secondaryWithIcon = ButtonData {
    ButtonConfig(buttonContent = {
        ButtonContentWithIcon(text = "Secondary button with icon")
    }, background = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
}

private val primaryWithoutIcon = ButtonData {
    ButtonConfig(buttonContent = {
        ButtonContentWithoutIcon(text = "Primary button without icon")
    }, background = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
}

private val secondaryWithoutIcon = ButtonData {
    ButtonConfig(buttonContent = {
        ButtonContentWithoutIcon(text = "Secondary button without icon")
    }, background = MaterialTheme.colorScheme.secondary, contentColor = MaterialTheme.colorScheme.onSecondary)
}

class ButtonConfigProvider : PreviewParameterProvider<ButtonData> {
    override val values: Sequence<ButtonData> = sequenceOf(
        primaryWithIcon, secondaryWithIcon, primaryWithoutIcon, secondaryWithoutIcon
    )
}

@Composable
fun RowScope.ButtonContentWithIcon(text: String) {
    Icon(imageVector = Icons.Outlined.Star, contentDescription = "", modifier = Modifier.padding(end = 8.dp))
    Text(text = text)
}

@Composable
fun RowScope.ButtonContentWithoutIcon(text: String) {
    Text(text = text)
}

@Composable
fun ButtonComponent(
    background: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope, Modifier) -> Unit,
) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = background,
        contentColor = contentColor,
    )
    Button(colors = buttonColors, onClick = onClick, modifier = modifier) {
        content(this, Modifier)
    }
}

@Composable
fun ChipComponent(
    background: Color,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope, Modifier) -> Unit,
) {
    val chipColors = ChipDefaults.chipColors(
        backgroundColor = background,
        contentColor = contentColor,
    )
    val chipBorder = ChipDefaults.chipBorder()
    Chip(
        colors = chipColors,
        border = chipBorder,
        onClick = onClick,
        modifier = modifier
    ) {
        content(this, Modifier)
    }
}

@Preview
@Composable
fun ButtonSolidPreview(
    @PreviewParameter(ButtonConfigProvider::class) data: ButtonData
) {
    ExperimentsTheme {
        ButtonComponent(
            onClick = {},
            background = data.value().background,
            contentColor = data.value().contentColor
        ) { rowScope, modifier ->
            data.value().buttonContent.invoke(rowScope, modifier)
        }
    }
}

@Preview
@Composable

fun ChipPreview(
    @PreviewParameter(ButtonConfigProvider::class) data: ButtonData
) {
    ExperimentsTheme {
        ChipComponent(
            onClick = {},
            background = data.value().background,
            contentColor = data.value().contentColor
        ) { rowScope, modifier ->
            data.value().buttonContent.invoke(rowScope, modifier)
        }
    }
}
