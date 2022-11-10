package com.example.moviecatalog.screens.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.*
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt


@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    if (viewModel.status.value.isLoading)
        LoadingProgress()
    else {
        Column {
            Banner(navController, viewModel.status)
            Gallery(navController, viewModel)
        }
    }
    if (FavoriteUpdateParameter.isFavouriteChange) {
        viewModel.changeFavoriteFromMovieScreen()
    }

    if (viewModel.status.value.isError) {
        Toast.makeText(LocalContext.current, viewModel.status.value.errorMessage, Toast.LENGTH_LONG).show()
        viewModel.setDefaultStatus()
    }
    if (viewModel.status.value.showMessage) {
        Toast.makeText(LocalContext.current, viewModel.status.value.textMessage, Toast.LENGTH_SHORT).show()
        viewModel.setDefaultStatus()
    }
}

@Composable
fun Banner(navController: NavController, status: MutableState<MainScreenStatus>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f),
        contentAlignment = Alignment.BottomCenter
    ) {
        GlideImage(
            imageModel = { status.value.items.getOrNull(0)?.poster },
            previewPlaceholder = R.drawable.logo,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            failure = {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.logo),
                        contentDescription = null
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            MaterialTheme.colors.background
                        )
                    )
                )
        )
        Button(
            onClick = {
                navController.navigate("${Screen.MovieScreen.route}/${status.value.items.getOrNull(0)?.id}") {
                    popUpTo(Screen.MainScreen.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(R.string.watching))
        }
    }
}

@Composable
fun Favourites(navController: NavController, viewModel: MainViewModel) {
    val favorites = viewModel.favorites.observeAsState()

    Text(
        text = stringResource(R.string.favour),
        style = MaterialTheme.typography.h1,
        modifier = Modifier.padding(top = 16.dp)
    )
    Spacer(modifier = Modifier.padding(4.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(favorites.value!!.size) { i ->
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.size(120.dp, 172.dp)
            ) {
                GlideImage(
                    imageModel = { favorites.value!![i].poster },
                    previewPlaceholder = R.drawable.logo,
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillHeight
                    ),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${Screen.MovieScreen.route}/${favorites.value!![i].id}") {
                                popUpTo(Screen.MainScreen.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                )
                Button(
                    onClick = {
                        viewModel.deleteFavorite(favorites.value!![i].id)
                    },
                    modifier = Modifier
                        .size(20.dp, 20.dp)
                        .padding(top = 4.dp, end = 4.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp, 12.dp)
                            .clip(CircleShape)
                            .background(colorResource(R.color.deleteFavour)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            ImageVector.vectorResource(R.drawable.delete_icon),
                            contentDescription = null,
                            modifier = Modifier.size(8.dp, 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Gallery(navController: NavController, viewModel: MainViewModel) {
    val state = viewModel.status
    val favorites = viewModel.favorites.observeAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        if (favorites.value!!.isNotEmpty()) {
            item {
                Favourites(navController, viewModel)
            }
        }
        item {
            Text(
                text = stringResource(R.string.gallery),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        items(state.value.items.size) { i ->
            if (i != 0) {
                if (i >= state.value.items.size - 1 && !state.value.endReached && !state.value.isMakingRequest) {
                    viewModel.loadPage()
                }
                var mark = 0f
                if (state.value.items[i].reviews != null && state.value.items[i].reviews!!.isNotEmpty()) {
                    for (review in state.value.items[i].reviews!!)
                        mark += review.rating
                    mark = (mark / state.value.items[i].reviews!!.size * 10).roundToInt() / 10f
                }
                var listGenres = ""

                Row(modifier = Modifier
                    .heightIn(min = 144.dp)
                    .clickable {
                        navController.navigate("${Screen.MovieScreen.route}/${state.value.items[i].id}") {
                            popUpTo(Screen.MainScreen.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 144.dp)
                    ) {
                        GlideImage(
                            imageModel = { state.value.items[i].poster },
                            previewPlaceholder = R.drawable.logo,
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.FillHeight
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Text(text = state.value.items[i].name, style = MaterialTheme.typography.h2)
                        Text(
                            text = "${state.value.items[i].year} • ${state.value.items[i].country}",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primaryVariant
                        )
                        if (state.value.items[i].genres != null) {
                            listGenres = state.value.items[i].genres!![0].name
                            for (genres in 1 until state.value.items[i].genres!!.size)
                                listGenres += ", ${state.value.items[i].genres!![genres].name}"
                            Text(
                                text = listGenres,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(
                                    top = calculateRatingOffset(
                                        state.value.items[i].name.length,
                                        listGenres.length
                                    )
                                ),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = if (mark != 0.0f) mark.toString() else "-",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant,
                                modifier = Modifier
                                    .size(56.dp, 28.dp)
                                    .clip(MaterialTheme.shapes.large)
                                    .background(
                                        Color(
                                            red = calculateRedByMark(mark),
                                            green = calculateGreenByMark(mark),
                                            blue = 0f
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
        item {
            if (state.value.isMakingRequest)
                LoadingProgress()
        }
    }
}