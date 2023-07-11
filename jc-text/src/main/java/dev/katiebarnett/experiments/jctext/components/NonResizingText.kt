package dev.katiebarnett.experiments.jctext.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.katiebarnett.experiments.core.theme.ExperimentsTheme


val standardFontSize = 16.sp
@Composable
fun RegularResizingText(
    textToDisplay: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = textToDisplay,
        fontSize = standardFontSize,
        modifier = modifier
    )
}

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

@Composable
fun NonResizingTextInteger(
    textToDisplay :String,
    modifier: Modifier = Modifier
) {
    Text(
        text = textToDisplay,
        fontSize = 16.nonScaledSp,
        modifier = modifier
    )
}

val TextUnit.nonScaledSp
    @Composable
    get() = (this.value / LocalDensity.current.fontScale).sp

@Composable
fun NonResizingTextSp(
    textToDisplay :String,
    modifier: Modifier = Modifier
) {
    Text(
        text = textToDisplay,
        fontSize = standardFontSize.nonScaledSp,
        modifier = modifier
    )
}

@Preview(
    name = "small font",
    group = "Font scaling",
    fontScale = 0.85f,
    showBackground = true
)
@Preview(
    name = "regular font",
    group = "Font scaling",
    fontScale = 1.0f,
    showBackground = true
)
@Preview(
    name = "large font",
    group = "Font scaling",
    fontScale = 1.15f,
    showBackground = true
)
@Preview(
    name = "extra large font",
    group = "Font scaling",
    fontScale = 1.3f,
    showBackground = true
)
annotation class FontScalePreviews

@FontScalePreviews
@Composable
fun RegularResizingTextPreview() {
    ExperimentsTheme {
        Box(modifier = Modifier.width(200.dp)) {
            RegularResizingText(textToDisplay = "This is regular resizing text with font scale ${LocalDensity.current.fontScale}")
        }
    }
}

@FontScalePreviews
@Composable
fun NonResizingTextPreview() {
    ExperimentsTheme {
        Box(modifier = Modifier.width(200.dp)) {
            NonResizingTextInteger(textToDisplay = "This is non-resizing text with font scale ${LocalDensity.current.fontScale} using integer to set font size")
        }
    }
}

@FontScalePreviews
@Composable
fun NonResizingTextSpPreview() {
    ExperimentsTheme {
        Box(modifier = Modifier.width(200.dp)) {
            NonResizingTextSp(textToDisplay = "This is non-resizing text with font scale ${LocalDensity.current.fontScale} using sp to set font size")
        }
    }
}