package dev.katiebarnett.experiments.holiday

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagManager
import dev.katiebarnett.experiments.holiday.featureflags.FeatureFlagStore.Companion.FLAG_ENABLE_CHRISTMAS_THEME
import dev.katiebarnett.experiments.holiday.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var featureFlagManager: FeatureFlagManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val featureFlags by viewModel.featureFlagFlow.collectAsStateWithLifecycle(mapOf())
            AppTheme(
                themeMode = AppThemeManager.getThemeMode(featureFlags)
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("My Favourite Trees") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { }) {
                            Icon(Icons.Filled.Add, "Add")
                        }
                    },
                    bottomBar = {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val formattedString = buildAnnotatedString {
                                append("Christmas theme toggle is now: ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(featureFlags[FLAG_ENABLE_CHRISTMAS_THEME]?.enabled.toString())
                                }
                            }

                            Text(
                                text = formattedString,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                ) { innerPadding ->
                    TreeList(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Tree(val name: String, val description: String, val rating: Int)

@Composable
fun TreeList(modifier: Modifier = Modifier) {
    val trees = listOf(
        Tree(
            "Oak",
            "A strong and sturdy tree known for its longevity and hard wood. It provides great shade.",
            5
        ),
        Tree(
            "Pine",
            "An evergreen coniferous tree that has clusters of needle-shaped leaves. Smells like Christmas.",
            4
        ),
        Tree(
            "Maple",
            "Famous for its distinct leaves and syrup. The autumn colors are breathtaking.",
            5
        ),
        Tree(
            "Birch",
            "Known for its distinctive white bark and delicate leaves. Adds a nice contrast to forests.",
            3
        ),
        Tree(
            "Willow",
            "Graceful tree with sweeping branches that often grow near water. Very poetic.",
            4
        ),
        Tree(
            "Redwood",
            "The tallest trees on Earth, ancient and majestic. Truly awe-inspiring giants.",
            5
        ),
        Tree(
            "Cherry Blossom",
            "Beautiful pink flowers in spring. A symbol of renewal and the fleeting nature of life.",
            5
        ),
        Tree(
            "Spruce",
            "A coniferous evergreen tree often used as a Christmas tree. Has a classic shape.",
            4
        )
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(trees) { tree ->
            TreeItem(tree)
        }
    }
}

@Composable
fun TreeItem(tree: Tree) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tree.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tree.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            RatingBar(rating = tree.rating)
        }
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < rating) {
                    Color(
                        0xFFFFD700
                    )
                } else {
                    MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f)
                },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TreeListPreview() {
    AppTheme {
        Surface {
            TreeList()
        }
    }
}

@Preview(name = "Tree Item Preview", showBackground = true)
@Composable
fun TreeItemPreview() {
    AppTheme {
        TreeItem(
            Tree(
                "Oak",
                "A strong and sturdy tree known for its longevity and hard wood. It provides great shade.",
                5
            )
        )
    }
}

@Preview(name = "Rating Bar Preview", showBackground = true)
@Composable
fun RatingBarPreview() {
    AppTheme {
        RatingBar(3)
    }
}
