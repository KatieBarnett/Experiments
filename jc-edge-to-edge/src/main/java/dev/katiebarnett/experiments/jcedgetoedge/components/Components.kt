package dev.katiebarnett.experiments.jcedgetoedge.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.katiebarnett.experiments.jcedgetoedge.data.ReefFact


@Composable
fun FactItem(fact: ReefFact, onClick: (ReefFact) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    onClick.invoke(fact)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = fact.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FactDetail(fact: ReefFact, modifier: Modifier = Modifier) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = fact.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = fact.fact,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}