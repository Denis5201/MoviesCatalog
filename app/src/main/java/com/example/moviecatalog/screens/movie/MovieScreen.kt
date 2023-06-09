package com.example.moviecatalog.screens.movie

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.*
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun MovieScreen(navController: NavController, viewModel: MovieViewModel, movieId: String?) {
    val status = viewModel.status.observeAsState()

    val state = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier,
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {

            val textSize = (24 + 12 * state.toolbarState.progress).sp
            val textStartPadding = (54 - 32 * state.toolbarState.progress).dp
            val textTopPadding = (10 + 38 * state.toolbarState.progress).dp

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .pin()
                    .height(56.dp)
                    .fillMaxWidth()
            )
            GlideImage(
                imageModel = { status.value!!.movieDetail?.poster },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter
                ),
                modifier = Modifier
                    .height(250.dp)
                    .alpha(state.toolbarState.progress),
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
            status.value!!.movieDetail?.name?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MaterialTheme.typography.body1.fontFamily,
                    fontSize = textSize,
                    color = MaterialTheme.colors.primaryVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = if (textSize > 26.sp) 4 else 1,
                    modifier = Modifier
                        .padding(
                            start = textStartPadding,
                            top = textTopPadding,
                            end = 40.dp,
                            bottom = 16.dp
                        )
                        .road(
                            whenCollapsed = Alignment.TopStart,
                            whenExpanded = Alignment.BottomStart
                        )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.MainScreen.route) {
                            if (viewModel.favoriteInStart.value != viewModel.status.value!!.isFavorite)
                                FavoriteUpdateParameter.isFavouriteChange = true
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
                IconButton(
                    onClick = {
                        viewModel.changeFavorite()
                    },
                    modifier = Modifier.alpha(
                        if (textSize < 26.sp) {
                            1f
                        } else 0f
                    ),
                    enabled = if (textSize < 26.sp) {
                        viewModel.heartEnable.observeAsState(true).value
                    } else false

                ) {
                    Icon(
                        imageVector = if (status.value!!.isFavorite) {
                            ImageVector.vectorResource(R.drawable.heart_filled)
                        } else {
                            ImageVector.vectorResource(R.drawable.heart)
                        },
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                }
            }
        }) {
        if (status.value!!.isLoading) {
            LoadingProgress()
        } else {
            MovieScreenBody(viewModel)
        }
        if (!status.value!!.isGetProfile && !status.value!!.makingRequest) {
            viewModel.beforeInit()
        } else if (status.value!!.isStart && !status.value!!.makingRequest) {
            viewModel.initialScreen(movieId!!)
        }
    }



    if (status.value!!.isError) {
        Toast.makeText(
            LocalContext.current,
            viewModel.status.value!!.errorMessage,
            Toast.LENGTH_LONG
        ).show()
        viewModel.setDefaultStatus()
    }
    if (status.value!!.showMessage) {
        Toast.makeText(
            LocalContext.current,
            viewModel.status.value!!.textMessage,
            Toast.LENGTH_SHORT
        ).show()
        viewModel.setDefaultStatus()
    }

    BackHandler {
        if (viewModel.favoriteInStart.value != viewModel.status.value!!.isFavorite)
            FavoriteUpdateParameter.isFavouriteChange = true
        navController.navigateUp()
    }
}

@Composable
fun MovieScreenBody(viewModel: MovieViewModel) {
    val status = viewModel.status.observeAsState()
    val isShow = viewModel.showDialog.observeAsState(false)

    if (isShow.value) ReviewDialog(viewModel, isShow)

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            ReviewText(viewModel)
        }
        status.value!!.movieDetail!!.reviews?.let {
            items(it) { review ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        .border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GlideImage(
                                imageModel = { review.author?.avatar },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                failure = {
                                    Image(
                                        bitmap = ImageBitmap.imageResource(R.drawable.avatar_default),
                                        contentDescription = null
                                    )
                                }
                            )
                            Column(modifier = Modifier.padding(start = 16.dp)) {
                                Text(
                                    text = if (review.isAnonymous || review.author == null) {
                                        stringResource(R.string.anonymous_user)
                                    } else {
                                        review.author.nickName
                                    },
                                    style = MaterialTheme.typography.h5,
                                    color = MaterialTheme.colors.primaryVariant
                                )
                                if ((review.author?.userId ?: stringResource(R.string.error))
                                    == viewModel.myReviewInfo.value?.author?.userId
                                ) {
                                    Text(
                                        text = stringResource(R.string.my_review),
                                        style = MaterialTheme.typography.h4,
                                        color = MaterialTheme.colors.secondaryVariant
                                    )
                                }
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
                                        .padding(top = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        review.reviewText?.let { text ->
                            Text(
                                text = text,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = parsingISO8601(review.createDateTime),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.secondaryVariant
                            )
                            if ((review.author?.userId ?: stringResource(R.string.error))
                                == viewModel.myReviewInfo.value?.author?.userId
                            ) {
                                Row {
                                    Image(
                                        bitmap = ImageBitmap.imageResource(R.drawable.edit_review),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                viewModel.changeShowDialog(!isShow.value)
                                            }
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Image(
                                        bitmap = ImageBitmap.imageResource(R.drawable.delete_review),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                viewModel.deleteReview()
                                            }
                                    )
                                }
                            }
                        }
                    }

                }
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
            NameDetail(idString = R.string.year)
            Text(
                text = "${movieDetails!!.year}",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.country)
            Text(
                text = if (movieDetails!!.country != null) movieDetails.country!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.time)
            Text(
                text = "${movieDetails!!.time} мин",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.tagline)
            Text(
                text = if (movieDetails!!.tagline != null) movieDetails.tagline!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.director)
            Text(
                text = if (movieDetails!!.director != null) movieDetails.director!! else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.budget)
            Text(
                text = if (movieDetails!!.budget != null) "$${spaceForNumber(movieDetails.budget!!)}" else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.fees)
            Text(
                text = if (movieDetails!!.fees != null) "$${spaceForNumber(movieDetails.fees!!)}" else "-",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Row(modifier = Modifier.padding(top = 8.dp)) {
            NameDetail(idString = R.string.age_limit)
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
fun ReviewText(viewModel: MovieViewModel) {
    val status = viewModel.status.observeAsState()
    val isShow = viewModel.showDialog.observeAsState(false)

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
            if (!status.value!!.userHaveReview) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.new_review),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        viewModel.changeShowDialog(!isShow.value)
                    }
                )
            }
        }
    }
}