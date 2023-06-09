package com.example.moviecatalog.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.moviecatalog.R

val IBMPlexSans = FontFamily(
    Font(R.font.ibm_plexsans)
)
val Montserrat = FontFamily(
    Font(R.font.montserrat)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    h1 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 1.sp,
        color = Orange
    ),
    h2 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = White
    ),
    h3 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = White
    ),
    h4 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h5 = TextStyle(
        fontFamily = IBMPlexSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h6 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)