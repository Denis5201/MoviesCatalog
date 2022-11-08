package com.example.moviecatalog.screens.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.*
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val status = viewModel.status.observeAsState()

    if (status.value!!.isLoading) {
        LoadingProgress()
    } else {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Header(viewModel)
            ProfileLines(viewModel)
            ProfileButtons(viewModel)
        }
    }

    if (status.value!!.isError) {
        Toast.makeText(LocalContext.current, viewModel.status.value!!.errorMassage, Toast.LENGTH_LONG).show()
        viewModel.setDefaultStatus()
    }
    if (!status.value!!.logout && status.value!!.showMessage) {
        Toast.makeText(LocalContext.current, viewModel.status.value!!.textMessage, Toast.LENGTH_SHORT).show()
        viewModel.setDefaultStatus()
    }
    if (status.value!!.logout) {
        Toast.makeText(LocalContext.current, viewModel.status.value!!.textMessage, Toast.LENGTH_SHORT).show()
        viewModel.setDefaultStatus()

        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun Header(viewModel: ProfileViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            imageModel = { "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png" },
            previewPlaceholder = R.drawable.logo,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = viewModel.nickName.observeAsState().value!!,
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun ProfileLines(viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.72f)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.mail),
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        OneInputLine(
            state = viewModel.mail.observeAsState(""), { viewModel.setMail(it) },
            name = stringResource(R.string.mail), isPassword = false
        )

        Text(
            text = stringResource(R.string.link_avatar),
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        OneInputLine(
            state = viewModel.avatar.observeAsState(""), { viewModel.setAvatar(it) },
            name = stringResource(R.string.link_avatar), isPassword = false
        )

        Text(
            text = stringResource(R.string.name),
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        OneInputLine(
            state = viewModel.name.observeAsState(""), { viewModel.setName(it) },
            name = stringResource(R.string.name), isPassword = false
        )

        Text(
            text = stringResource(R.string.date_birthday),
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        DateDialog(state = viewModel.date.observeAsState(""), valChange = { viewModel.setDate(it) })

        Text(
            text = stringResource(R.string.gender),
            modifier = Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
        Spacer(modifier = Modifier.padding(4.dp))
        ChoosingGender(
            state = viewModel.selectGender.observeAsState(2),
            valChange = { viewModel.setSelectGender(it) }
        )
    }
}

@Composable
fun ProfileButtons(viewModel: ProfileViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(
                bottom = 16.dp,
                top = 32.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        FirstButton(
            name = stringResource(R.string.save),
            state = viewModel.save.observeAsState(false),
            click = {
                viewModel.putProfile()
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = {
                      viewModel.logout()
            },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(0.dp, MaterialTheme.colors.background),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            )
        ) {
            Text(
                text = stringResource(R.string.exit),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}