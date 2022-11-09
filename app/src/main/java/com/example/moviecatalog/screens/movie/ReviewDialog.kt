package com.example.moviecatalog.screens.movie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.moviecatalog.R

@Composable
fun ReviewDialog(viewModel: MovieViewModel, state: State<Boolean>) {
    val stars = viewModel.stars.observeAsState().value
    val status = viewModel.status.observeAsState()
    val isAnonymous = viewModel.isAnonymous.observeAsState(false)

    Dialog(
        onDismissRequest = { viewModel.changeShowDialog(!state.value) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .size(340.dp, 410.dp)
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
                            .size(29.dp)
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
                    .height(24.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.anonymous_review),
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.primaryVariant
                )
                Box(
                    modifier =  Modifier
                        .size(24.dp)
                        .border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.small)
                        .clickable { viewModel.changeIsAnonymous(!isAnonymous.value) },
                    contentAlignment = Alignment.Center
                ) {
                    if (isAnonymous.value) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.check),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp, 12.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        if (status.value!!.userHaveReview) viewModel.changeReview() else viewModel.addReview()
                        viewModel.changeShowDialog(!state.value)
                    },
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