package com.example.moviecatalog.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.domain.Movie
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun MainScreen(navController: NavController) {
    Column {
        Banner()
        Gallery()
    }
}

var movies = listOf(
    Movie("1", "Звёдзные войны: Эпизод 8 sdfgsdfgsdgggsdgdssdfgsdgsfgsdgdsgfdssdgds", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 1.0),
    Movie("2", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 2.0),
    Movie("3", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 3.0),
    Movie("4", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 4.2),
    Movie("5", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 5.0),
    Movie("6", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 5.5),
    Movie("7", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 6.2),
    Movie("8", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 7.5),
    Movie("9", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 9.0),
    Movie("10", "ЗВ", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA", listOf("фантастика", "боевик"), 10.0)
)

@Composable
fun Banner() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.4f),
        contentAlignment = Alignment.BottomCenter
    ) {
        GlideImage(
            imageModel = { movies[0].poster },
            previewPlaceholder = R.drawable.logo,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillHeight
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
        Button(
            onClick = {  },
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Text(text = stringResource(R.string.watching))
        }
    }
}

@Composable
fun Favourites() {
    Text(text = stringResource(R.string.favour),
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(top = 16.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))
    LazyRow {
        items(movies) { item ->
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.size(120.dp, 172.dp)
            ) {
                GlideImage(
                    imageModel = { item.poster },
                    previewPlaceholder = R.drawable.logo,
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillHeight
                    ),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
                Button(
                    onClick = { },
                    modifier = Modifier
                        .size(12.dp, 12.dp)
                        .padding(top = 2.dp, end = 2.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.deleteFavour)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(ImageVector.vectorResource(R.drawable.delete_icon), null)
                }
            }
        }
    }
}

@Composable
fun Gallery() {
    LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        item {
            Favourites()
        }
        item {
            Text(text = stringResource(R.string.gallery),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = 16.dp))
            Spacer(modifier = Modifier.padding(8.dp))
        }
        items(movies) { item ->
            Row(modifier = Modifier.heightIn(min = 144.dp)) {
                Box(
                    modifier = Modifier
                        .size(100.dp, 144.dp)
                ) {
                    GlideImage(
                        imageModel = { item.poster },
                        previewPlaceholder = R.drawable.logo,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.FillHeight
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    )
                }
                Column(modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                ) {
                    Text(text = item.name, style = MaterialTheme.typography.h2)
                    Text(
                        text = "${item.year} • ${item.country}",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primaryVariant
                    )
                    Text(
                        text = item.genres.toString().drop(1).dropLast(1),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primaryVariant
                    )
                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = if (item.name.length < 20) 40.dp else if (item.name.length < 40) 20.dp else 0.dp),
                        verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = item.mark.toString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primaryVariant,
                            modifier = Modifier
                                .size(56.dp, 28.dp)
                                .clip(MaterialTheme.shapes.large)
                                .background(
                                    Color(
                                        red = if (item.mark > 5) (5 / item.mark - item.mark / 38).toFloat() else (0.9 - item.mark / 150).toFloat(),
                                        green = if (item.mark > 5) (0.9 - item.mark / 100).toFloat() else (item.mark / 5 - item.mark / 25).toFloat(),
                                        blue = 0f
                                    )
                                )
                        )
                    }
                }
            }

        }
    }

}