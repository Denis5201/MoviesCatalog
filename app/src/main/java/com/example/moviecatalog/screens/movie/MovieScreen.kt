package com.example.moviecatalog.screens.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.domain.MovieDetail
import com.example.moviecatalog.domain.Review
import com.example.moviecatalog.domain.UserShort
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

private val movieDetail = MovieDetail(
    "1", "Звёдзные войны: Последние джедаи (Эпизод 8)", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA",
    listOf("фантастика", "боевик", "фантастика", "боевик", "фантастика", "боевик"),
    listOf(Review("1", 4, "Фильм как фильм", false, "10.01.2018", UserShort("123", "Test", "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png")), Review("1", 4, "Ещё какой-то большооооййй отзыв. бла, бла, бла ...... .........", false, "10.01.2018", UserShort("123", "Test", "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png"))),
    152, "«Let the Past Die»",
    "Новая история о противостоянии света и тьмы, добра и зла начинается после гибели Хана Соло. В Галактике, где Первый Орден и Сопротивление яростно сражаются друг с другом в войне, героиня Рей пробудила в себе Силу. Но что произойдет, когда она встретится с единственным оставшимся в живых рыцарем-джедаем - Люком Скайуокером?",
    "Райан Джонсон", 317000000, 1332539889, 16
)

@Composable
fun MovieScreen(navController: NavController) {
    val lazyState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyState.firstVisibleItemIndex == 0
        }
    }

    Column() {
        Box(modifier = Modifier.background(MaterialTheme.colors.background).fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth().height(50.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(Screen.MainScreen.route) {
                                saveState = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                Text(
                    text = movieDetail.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.fillMaxWidth(0.87f).alpha(
                        if (!firstItemVisible) {
                            1f
                        } else 0f
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier.alpha(
                        if (!firstItemVisible) {
                            1f
                        } else 0f
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.heart),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
            /*.nestedScroll(nestedScrollConnection)*/,
            state = lazyState
        ) {
            item {
                Box(contentAlignment = Alignment.BottomStart) {
                    GlideImage(
                        imageModel = { movieDetail.poster },
                        previewPlaceholder = R.drawable.logo,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopCenter
                        ),
                        modifier = Modifier
                            .height(250.dp)
                    )
                    Text(
                        text = movieDetail.name,
                        fontWeight = FontWeight.Bold,
                        fontFamily = MaterialTheme.typography.body1.fontFamily,
                        fontSize = 36.sp,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    )
                }

            }
            item {
                Text(
                    text = movieDetail.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
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
    }


@Composable
fun AboutMovie() {
    Column(modifier = Modifier
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
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
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        .fillMaxWidth()) {
        Text(text = "Жанры", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primaryVariant)
        FlowRow(modifier = Modifier.padding(top = 16.dp),
            mainAxisAlignment = FlowMainAxisAlignment.Start,
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            movieDetail.genres.forEach {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .padding(16.dp, 6.dp, 16.dp, 6.dp)
                )
            }
        }
    }
}

@Composable
fun Reviews() {
    Column(modifier = Modifier
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        .fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Отзывы", style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primaryVariant)
            Image(imageVector = ImageVector.vectorResource(R.drawable.new_review), contentDescription = null)
        }

        movieDetail.reviews.forEach { review ->
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        GlideImage(
                            imageModel = { review.author.avatar },
                            previewPlaceholder = R.drawable.logo,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(
                                text = if (review.isAnonymous) "Анонимный пользователь" else review.author.nickName,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.primaryVariant
                            )
                            Text(text = "мой отзыв", style = MaterialTheme.typography.h4, color = MaterialTheme.colors.secondaryVariant)
                        }

                        Box(modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Text(
                                text = review.rating.toString(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant,
                                modifier = Modifier
                                    .size(42.dp, 28.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(
                                        Color(
                                            red = if (review.rating > 5) (5 / review.rating.toFloat() - review.rating.toFloat() / 38) else (0.9 - review.rating / 150).toFloat(),
                                            green = if (review.rating > 5) (0.9 - review.rating / 100).toFloat() else (review.rating.toFloat() / 5 - review.rating.toFloat() / 25),
                                            blue = 0f
                                        )
                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = review.reviewText, style = MaterialTheme.typography.body1, color = MaterialTheme.colors.primaryVariant)
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = review.createDateTime, style = MaterialTheme.typography.body1, color = MaterialTheme.colors.secondaryVariant)
                        Row() {
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.edit_review), contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.delete_review), contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

            }
        }

    }
}
