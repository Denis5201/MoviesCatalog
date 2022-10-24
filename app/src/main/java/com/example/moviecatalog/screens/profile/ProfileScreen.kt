package com.example.moviecatalog.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.*
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
        Header()
        ProfileLines(viewModel)
        ProfileButtons(navController, viewModel)
    }
}

@Composable
fun Header() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            imageModel = { "https://www.pinclipart.com/picdir/middle/105-1058105_tae-kwon-do-clipart.png" },
            previewPlaceholder = R.drawable.logo,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Тест",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
fun ProfileLines(viewModel: ProfileViewModel) {
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
        state = viewModel.selectGender.observeAsState(0),
        valChange = { viewModel.setSelectGender(it) })
}

@Composable
fun ProfileButtons(navController: NavController, viewModel: ProfileViewModel) {
    Column(
        modifier = Modifier
            .padding(
                bottom = 15.dp,
                top = 5.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        FirstButton(
            name = stringResource(R.string.save),
            navController = navController,
            state = viewModel.save.observeAsState(false)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        SecondButton(
            name = stringResource(R.string.exit),
            navController = navController,
            route = Screen.LoginScreen.route,
            isSave = false
        )
    }
}