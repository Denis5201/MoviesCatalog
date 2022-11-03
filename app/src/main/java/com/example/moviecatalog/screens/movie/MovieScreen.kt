package com.example.moviecatalog.screens.movie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.domain.MovieDetail
import com.example.moviecatalog.domain.Review
import com.example.moviecatalog.domain.UserShortModel
import com.example.moviecatalog.screens.LoadingProgress
import com.example.moviecatalog.screens.calculateGreenByMark
import com.example.moviecatalog.screens.calculateRedByMark
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

private val movieDetail = MovieDetail(
    "1", "Звёдзные войны: Последние джедаи (Эпизод 8)", "https://avatars.mds.yandex.net/get-kinopoisk-image/1599028/e9bc098b-43fd-446a-a3dd-9b37b8e2a8ec/300x450", 2017, "USA",
    listOf("фантастика", "боевик", "фантастика", "боевик", "фантастика", "боевик"),
    listOf(Review("1", 4, "Фильм как фильм", false, "10.01.2018", UserShortModel("123", "Test", "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png")), Review("1", 4, "Ещё какой-то большооооййй отзыв. бла, бла, бла ...... .........", false, "10.01.2018", UserShortModel("123", "Test", "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png"))),
    152, "«Let the Past Die»",
    "Новая история о противостоянии света и тьмы, добра и зла начинается после гибели Хана Соло. В Галактике, где Первый Орден и Сопротивление яростно сражаются друг с другом в войне, героиня Рей пробудила в себе Силу. Но что произойдет, когда она встретится с единственным оставшимся в живых рыцарем-джедаем - Люком Скайуокером?",
    "Райан Джонсон", 317000000, 1332539889, 16
)


@Composable
fun MovieScreen(navController: NavController, viewModel: MovieViewModel, movieId: String?) {
    if (viewModel.status.observeAsState().value?.isLoading == true) {
        LoadingProgress()
    } else {
        MovieScreenBody(navController, viewModel)
    }
    if (viewModel.status.observeAsState().value?.isStart == true) {
        viewModel.initialScreen(movieId!!)
    }
}

@Composable
fun MovieScreenBody(navController: NavController, viewModel: MovieViewModel) {
    val lazyState = rememberLazyListState()
    val firstItemVisible by remember {
        derivedStateOf {
            lazyState.firstVisibleItemIndex == 0
        }
    }
    val status = viewModel.status.observeAsState()

    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp), verticalAlignment = Alignment.CenterVertically
            ) {
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
                    text = status.value!!.movieDetail!!.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .fillMaxWidth(0.87f)
                        .alpha(
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
                        imageModel = { status.value!!.movieDetail!!.poster},
                        previewPlaceholder = R.drawable.logo,
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.TopCenter
                        ),
                        modifier = Modifier
                            .height(250.dp)
                    )
                    Text(
                        text = status.value!!.movieDetail!!.name,
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
                    text = if (status.value!!.movieDetail!!.description != null) status.value!!.movieDetail!!.description!! else "Описания нет",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )
            }
            item {
                AboutMovie(viewModel)
            }
            item {
                Genres(viewModel)
            }
            item {
                Reviews(viewModel)
            }
        }
    }
}

@Composable
fun AboutMovie(viewModel: MovieViewModel) {
    val movieDetails = viewModel.status.observeAsState().value!!.movieDetail

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.about_movie),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant
        )
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.year),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetails!!.year}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.country),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = if (movieDetails!!.country != null) movieDetails.country!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.time),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetails!!.time} мин",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.tagline),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = if (movieDetails!!.tagline != null) movieDetails.tagline!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.director),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = if (movieDetails!!.director != null) movieDetails.director!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.budget),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "$${movieDetails!!.budget}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.fees),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "$${movieDetails!!.fees}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.age_limit),
                style = MaterialTheme.typography.h6,
                color = colorResource(R.color.about),
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = "${movieDetails!!.ageLimit}+",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
fun Genres(viewModel: MovieViewModel) {
    val movieDetails = viewModel.status.observeAsState().value!!.movieDetail

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.genres),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant
        )
        FlowRow(
            modifier = Modifier.padding(top = 16.dp),
            mainAxisAlignment = FlowMainAxisAlignment.Start,
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            movieDetails!!.genres?.forEach {
                Text(
                    text = it.name,
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
fun Reviews(viewModel: MovieViewModel) {
    val isShow = viewModel.showDialog.observeAsState(false)

    if (isShow.value) ReviewDialog(viewModel, isShow)

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.reviews),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.primaryVariant
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.new_review),
                contentDescription = null,
                modifier = Modifier.clickable {
                    viewModel.changeShowDialog(!isShow.value)
                }
            )
        }

        movieDetail.reviews.forEach { review ->
            Box(
                modifier = Modifier
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
                                text = if (review.isAnonymous) stringResource(R.string.anonymous_user) else review.author.nickName,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.primaryVariant
                            )
                            Text(
                                text = stringResource(R.string.my_review),
                                style = MaterialTheme.typography.h4,
                                color = MaterialTheme.colors.secondaryVariant
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
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
                                            red = calculateRedByMark(review.rating.toFloat()),
                                            green = calculateGreenByMark(review.rating.toFloat()),
                                            blue = 0f
                                        )
                                    )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = review.reviewText,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primaryVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = review.createDateTime,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.secondaryVariant
                        )
                        Row {
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.edit_review),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.delete_review),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun ReviewDialog(viewModel: MovieViewModel, state: State<Boolean>) {
    val stars = viewModel.stars.observeAsState().value

    Dialog(
        onDismissRequest = { viewModel.changeShowDialog(!state.value) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .size(328.dp, 410.dp)
                .clip(MaterialTheme.shapes.large)
                .background(colorResource(R.color.dialog))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.send_review),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primaryVariant,
            )
            Row(modifier = Modifier.padding(top = 12.dp)) {
                for (i in 0..9) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 6.dp)
                            .clickable { viewModel.setActiveStars(i) }
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.background_star_svg),
                            contentDescription = null,
                            alpha = if (stars!![i]) 1f else 0f,
                            modifier = Modifier.fillMaxSize(),
                            alignment = Alignment.TopStart
                        )
                        Image(
                            imageVector = if (stars[i]) {
                                ImageVector.vectorResource(R.drawable.enable_star_svg)
                            } else {
                                ImageVector.vectorResource(R.drawable.disenable_star_svg)
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .size(22.dp)
                                .padding(start = 2.dp, top = 2.dp),
                            alignment = Alignment.Center
                        )
                    }
                }
            }
            TextField(
                value = viewModel.textDialogReview.observeAsState("").value,
                onValueChange = { viewModel.setTextDialogReview(it) },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(296.dp, 120.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.review),
                        color = colorResource(R.color.dialog_placeholder),
                        fontFamily = MaterialTheme.typography.h6.fontFamily,
                        fontSize = 14.sp
                    )
                },
                textStyle = TextStyle(
                    fontFamily = MaterialTheme.typography.h6.fontFamily,
                    fontSize = 14.sp,
                    color = colorResource(R.color.background)
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    focusedIndicatorColor = Color(0f, 0f, 0f, 0f)
                )
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(24.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.anonymous_review),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primaryVariant
                )
                Checkbox(
                    checked = viewModel.isAnonymous.observeAsState(false).value,
                    onCheckedChange = { viewModel.changeIsAnonymous(it) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.background,
                        uncheckedColor = MaterialTheme.colors.secondary,
                        checkmarkColor = MaterialTheme.colors.primary
                    )
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = { viewModel.changeShowDialog(!state.value) }, //+запрос
                    modifier = Modifier.fillMaxWidth(),
                    border = if (viewModel.save.observeAsState(false).value) {
                        null
                    } else {
                        BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant)
                    },
                    colors = ButtonDefaults.buttonColors(
                        disabledBackgroundColor = colorResource(R.color.dialog)
                    ),
                    enabled = viewModel.save.observeAsState(false).value
                ) {
                    Text(
                        text = stringResource(R.string.save),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                        color = if (viewModel.save.observeAsState(false).value) {
                            MaterialTheme.colors.primaryVariant
                        } else {
                            MaterialTheme.colors.primary
                        }
                    )
                }

                Spacer(modifier = Modifier.padding(4.dp))

                Button(
                    onClick = { viewModel.changeShowDialog(!state.value) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(R.color.dialog)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}