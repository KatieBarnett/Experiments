package dev.katiebarnett.experiments.jccardflip.models

import androidx.compose.ui.graphics.Color

sealed class CardFace

class CardFront(
    val drawableRes: Int, 
    val cardColor: Color,
    val valueRes: Int
): CardFace() 

object CardBack: CardFace()