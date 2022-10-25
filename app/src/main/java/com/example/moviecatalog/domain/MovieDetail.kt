package com.example.moviecatalog.domain

data class MovieDetail (
    val id: String,
    val name: String,
    val poster: String,
    val year: Int,
    val country: String,
    val genres: List<String>,
    val reviews: List<String>,
    val time: Int,
    val tagline: String,
    val description: String,
    val director: String,
    val budget: Int,
    val fees: Int,
    val ageLimit: Int,
)