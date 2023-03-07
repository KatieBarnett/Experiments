package dev.katiebarnett.experiments.jcscrollstate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnVersion() {

    val listState = rememberScrollState()
    val scrolledAmount = listState.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = stringResource(R.string.title_column, scrolledAmount))
                        Text(text = stringResource(R.string.sub_title_column), style = MaterialTheme.typography.displaySmall)
                    }
                     },
                colors = topAppBarColors()
            )
        },
        modifier = Modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(listState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            facts.forEach {
                FactItem(it)
            }
        }
    }
}
