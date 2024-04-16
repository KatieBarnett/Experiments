package dev.katiebarnett.experiments.jcpreviews.components

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dev.katiebarnett.experiments.jcpreviews.R
import dev.katiebarnett.experiments.jcpreviews.viewmodels.MainViewModel

@Composable
fun TextComponent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            if (LocalInspectionMode.current) {
                "This is only displayed in a preview"
            } else {
                "This is only displayed in live code"
            }
        )
        Text("This should always be displayed")
    }
}

@Composable
fun ImageComponent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        val imageUrl = "https://img.goodfon.com/original/800x600/e/12/voda-more-aysberg-nebo.jpg"
        if (LocalInspectionMode.current) {
            Image(
                painter = painterResource(id = R.drawable.iceberg_preview),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // Show this text in the app:
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AnalyticsComponent() {
    if (!LocalInspectionMode.current) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle())
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("This is a component that contains an analytics call that runs on display")
    }
}

@Composable
fun ViewModelComponent() {
    val viewModel: MainViewModel = hiltViewModel()
    val list: List<String> = if (!LocalInspectionMode.current) {
        viewModel.someViewModelFlow.collectAsState(initial = emptyList()).value
    } else {
        listOf("d", "e", "f")
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        Text("From the view model flow: ")
        list.forEach {
            Text(it)
        }
    }
}

@Composable
fun ExampleScreen() {
    Column {
        TextComponent()
        ImageComponent()
        AnalyticsComponent()
        ViewModelComponent()
    }
}

@Preview(showBackground = true)
@Composable
fun TextComponentPreview() {
    TextComponent()
}

@Preview(showBackground = true)
@Composable
fun ImageComponentPreview() {
    ImageComponent()
}

@Preview(showBackground = true)
@Composable
fun AnalyticsComponentPreview() {
    AnalyticsComponent()
}

@Preview(showBackground = true)
@Composable
fun ViewModelComponentPreview() {
    ViewModelComponent()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExampleScreenPreview() {
    ExampleScreen()
}
