package dev.katiebarnett.experiments.jctopappbar.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dev.katiebarnett.experiments.jctopappbar.R


@Composable
fun NavigationIcon(navController: NavController) {
    if (navController.previousBackStackEntry != null) {
        IconButton(
            onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.navigate_back)
            )
        }
    }
}

@Composable
fun AboutActionIcon(navController: NavController) {
    IconButton(
        onClick = {  }) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = stringResource(id = R.string.navigate_about)
        )
    }
}