package com.example.moviecatalog.screens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

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

fun spaceForNumber(numberForSplit: Int): String {
    var result = ""
    val number = numberForSplit.toString()
    var count = 0
    for (i in number.length - 1 downTo 0) {
        count++
        result = number[i] + result
        if (count == 3 && i != 0) {
            result = " $result"
            count = 0
        }
    }
    return result
}

fun parsingISO8601(dateFromServer: String): String {
    var inputDate = dateFromServer
    if (inputDate.last() != 'Z') inputDate += 'Z'

    val dateToFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateToFormat.format(Date.from(Instant.parse(inputDate)))
}