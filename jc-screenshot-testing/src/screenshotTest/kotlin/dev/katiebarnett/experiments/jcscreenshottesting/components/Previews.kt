package dev.katiebarnett.experiments.jcscreenshottesting.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun TextComponentPreview() {
    TextComponent("Screenshot Test")
}

@Preview(showBackground = true)
@Composable
fun ImageComponentPreview() {
    ImageComponent()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleScreenPreview() {
    ExampleScreen("Screenshot Test")
}
