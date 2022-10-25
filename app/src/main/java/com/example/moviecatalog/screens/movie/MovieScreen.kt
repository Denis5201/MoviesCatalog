package com.example.moviecatalog.screens.movie

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.MovieDetail

private val movieDetail = MovieDetail(
    "1", "Звёздные войны", "", 2017, "USA",
    listOf("фантастика", "боевик"), listOf("review 1", "review 2"), 152, "«Let the Past Die»",
    "Новая история о противостоянии света и тьмы, добра и зла начинается после гибели Хана Соло. В Галактике, где Первый Орден и Сопротивление яростно сражаются друг с другом в войне, героиня Рей пробудила в себе Силу. Но что произойдет, когда она встретится с единственным оставшимся в живых рыцарем-джедаем - Люком Скайуокером?",
    "Райан Джонсон", 317000000, 1332539889, 16
)

@Composable
fun MovieScreen(navController: NavController) {
    LazyColumn(modifier = Modifier
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        .fillMaxWidth()
    ) {
        item { 
            Text(
                text = movieDetail.description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        item {
            AboutMovie()
        }
        item {
            Genres()
        }
        item {
            Reviews()
        }
    }
}

@Composable
fun AboutMovie() {
    Column(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth()) {
        Text(text = "О фильме", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primaryVariant)
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Год",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetail.year}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Страна",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = movieDetail.country,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Время",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetail.time} мин",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Слоган",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = movieDetail.tagline,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Режиссёр",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = movieDetail.director,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Бюджет",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "$${movieDetail.budget}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Сборы в мире",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "$${movieDetail.fees}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = "Возраст",
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetail.ageLimit}+",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
fun Genres() {
    Column(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth()) {
        Text(text = "Жанры", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primaryVariant)
    }
}

@Composable
fun Reviews() {
    Column(modifier = Modifier
        .padding(top = 16.dp)
        .fillMaxWidth()) {
        Text(text = "Отзывы", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primaryVariant)
        Box(modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)) {
            Column(
                modifier = Modifier
                .fillMaxWidth()) {
                Text(text = "ой-ой-ой")
                Text(text = "ой-ой-ой-ой-ой")
                Text(text = "ой-ой-ой")
                Text(text = "ой-ой-ой")
            }

        }
    }
}
