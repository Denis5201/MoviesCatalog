package com.example.moviecatalog.screens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun calculateRedByMark(mark: Float): Float {
    return if (mark > 5) {
        (5 / mark - mark / 38)
    } else {
        (0.9 - mark / 150).toFloat()
    }
}

fun calculateGreenByMark(mark: Float): Float {
    return if (mark > 5) {
        (0.9 - mark / 100).toFloat()
    } else {
        (mark / 5 - mark / 25)
    }
}

fun calculateRatingOffset(title: Int, genres: Int): Dp {
    val size = title + genres * 0.65

    return if (size < 40) 40.dp
    else if (size < 55) 20.dp
    else 2.dp
}