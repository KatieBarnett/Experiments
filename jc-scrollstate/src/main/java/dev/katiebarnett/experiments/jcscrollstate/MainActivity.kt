package dev.katiebarnett.experiments.jcscrollstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

            ReefTheme {
//                ColumnVersion()
//                LazyColumnVersion()
//                CollapsingHeaderVersion()
//                ScrollBehaviourVersion()
//                DismissibleAppBar()


                ScrollBehaviourHeaderLazyColumnVersion()
            }
        }
    }
}

