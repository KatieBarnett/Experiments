package dev.katiebarnett.experiments.core.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimen {

    val spacing = 8.dp
    val spacingDouble = spacing * 2
    val spacingHalf = spacing / 2

    object Button {
        val spacing = 8.dp
        val iconSize = 24.dp
        val iconSpacing = 8.dp
        val iconButtonSize = 30.dp
        val iconButtonIconSize = 20.dp
        val iconButtonContentPadding = 0.dp
    }

    object SavedGame {
        val iconSize = 24.dp
        val iconSpacing = 8.dp
    }
    
    object Card {
        val radius = 8.dp
        val border = 2.dp
        val spacing = 8.dp
    }

    object Dialog {
        val radius = 8.dp
    }

    object Solo {
        object InstructionText {
            val lineHeight = 24.dp
        }
        object AstraCardsText {
            val textSize = 36.sp
        }
    }
}