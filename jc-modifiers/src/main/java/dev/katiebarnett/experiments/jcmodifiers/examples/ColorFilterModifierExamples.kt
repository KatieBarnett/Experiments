package dev.katiebarnett.experiments.jcmodifiers.examples

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.jcmodifiers.R.drawable
import dev.katiebarnett.experiments.jcmodifiers.components.Sunlight
import dev.katiebarnett.experiments.jcmodifiers.util.colorfilter
import dev.katiebarnett.experiments.jcmodifiers.util.conditional
import dev.katiebarnett.experiments.jcmodifiers.util.disabled
import dev.katiebarnett.experiments.jcmodifiers.util.greyScale
import java.lang.String

val Pink = Color(0xffE67175)

@Composable
fun RowScope.SceneContent(modifier: Modifier = Modifier, color: Color) {
    Column(modifier.weight(1f).background(Sunlight)) {
        Image(
            painterResource(drawable.outline_yard_24),
            "Scene",
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        val hexColor = String.format("#%06X", 0xFFFFFF and color.toArgb())
        Text(
            text = "This is $hexColor image and text",
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
    }
}

@Composable
fun RowScope.PhotoSceneContent(modifier: Modifier = Modifier, color: Color) {
    Column(modifier.weight(1f).background(Sunlight)) {
        Image(
            painterResource(drawable.rose),
            "Scene",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        val hexColor = String.format("#%06X", 0xFFFFFF and color.toArgb())
        Text(
            text = "This is $hexColor text and a photo\n",
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
    }
}

@Composable
fun RowScope.PhotoSceneContentManualGrey(modifier: Modifier = Modifier, showGrey: Boolean) {
    val backgroundColor = if (showGrey) {
        Color.LightGray
    } else {
        Sunlight
    }
    val textColor = if (showGrey) {
        Color.Gray
    } else {
        Pink
    }
    val photoColorFilter = if (showGrey) {
        ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
    } else {
        null
    }
    Column(modifier.weight(1f).background(backgroundColor)) {
        Image(
            painter = painterResource(drawable.rose),
            contentDescription = "Scene",
            colorFilter = photoColorFilter,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        val hexColor = String.format("#%06X", 0xFFFFFF and Pink.toArgb())
        Text(
            text = "This is $hexColor text and a photo\n",
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        )
    }
}

@Composable
fun ColorfulScene(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        SceneContent(color = Color.Blue)
        SceneContent(color = Color.Red)
        SceneContent(color = Color.Black)
        PhotoSceneContent(color = Pink)
    }
}

@Composable
fun GreyscaleScene(modifier: Modifier = Modifier) {
    Row(modifier.greyScale()) {
        SceneContent(color = Color.Blue)
        SceneContent(color = Color.Red)
        SceneContent(color = Color.Black)
        PhotoSceneContent(color = Pink)
    }
}

@Composable
fun GreyscaleSceneWithOpacity(
    modifier: Modifier = Modifier
) {
    Row(modifier.disabled()) {
        SceneContent(color = Color.Blue)
        SceneContent(color = Color.Red)
        SceneContent(color = Color.Black)
        PhotoSceneContent(color = Pink)
    }
}

@Composable
fun DisabledScene(
    isDisabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier.conditional(isDisabled, {
            disabled()
        })
    ) {
        SceneContent(color = Color.Blue)
        SceneContent(color = Color.Red)
        SceneContent(color = Color.Black)
        PhotoSceneContent(color = Pink)
    }
}

@Composable
fun GreyscaleSceneWithPhotoBasic(
    modifier: Modifier = Modifier
) {
    val greyScaleFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
    Row(modifier) {
        Image(
            painterResource(drawable.rose),
            "Scene",
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(drawable.rose),
            contentDescription = "Scene",
            colorFilter = greyScaleFilter,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RedScene(
    modifier: Modifier = Modifier
) {
    val groupModifier = modifier
        .colorfilter(1f, 0f, 0f)
        .background(Sunlight)
    Row(groupModifier) {
        SceneContent(color = Color.Blue)
        SceneContent(color = Color.Red)
        SceneContent(color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun ColorfulScenePreview() {
    Box(Modifier.padding(8.dp)) {
        ColorfulScene()
    }
}

@Preview(showBackground = true)
@Composable
fun GreyscaleSceneWithPhotoBasicPreview() {
    Box(Modifier.padding(8.dp)) {
        GreyscaleSceneWithPhotoBasic()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoSceneContentManualGreyPreview() {
    Row(Modifier.padding(8.dp)) {
        PhotoSceneContentManualGrey(showGrey = true)
    }
}

@Preview(showBackground = true)
@Composable
fun GreyscaleScenePreview() {
    Box(Modifier.padding(8.dp)) {
        GreyscaleScene()
    }
}

@Preview(showBackground = true)
@Composable
fun GreyscaleSceneWithOpacityPreview() {
    Box(Modifier.padding(8.dp)) {
        GreyscaleSceneWithOpacity()
    }
}

@Preview(showBackground = true)
@Composable
fun RedScenePreview() {
    Box(Modifier.padding(8.dp)) {
        RedScene()
    }
}
