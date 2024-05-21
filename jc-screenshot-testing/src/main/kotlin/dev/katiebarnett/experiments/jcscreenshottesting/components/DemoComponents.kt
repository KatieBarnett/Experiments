package dev.katiebarnett.experiments.jcscreenshottesting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.jcscreenshottesting.R

@Composable
fun TextComponent(previewType: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Hello world! - $previewType")
    }
}

@Composable
fun ImageComponent() {
    Image(
        painter = painterResource(id = R.drawable.globe),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ExampleScreen(previewType: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ImageComponent()
        TextComponent(previewType)
    }
}

@Preview(showBackground = true)
@Composable
fun TextComponentPreview() {
    TextComponent("In File")
}

@Preview(showBackground = true)
@Composable
fun ImageComponentPreview() {
    ImageComponent()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleScreenPreview() {
    ExampleScreen("In File")
}
