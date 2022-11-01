package com.example.moviecatalog.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.FirstButton
import com.example.moviecatalog.screens.OneInputLine
import com.example.moviecatalog.screens.SecondButton

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    Column {
        Logo()
        InputLines(viewModel)
        Buttons(navController, viewModel)
    }
}

@Composable
fun Logo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth()
        )
    }
}

@Composable
fun InputLines(viewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                top = 40.dp,
                end = 15.dp
            )
            .fillMaxWidth()
    ) {
        OneInputLine(
            state = viewModel.name.observeAsState(""), { viewModel.setName(it) },
            name = stringResource(R.string.login), isPassword = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(
            state = viewModel.password.observeAsState(""), { viewModel.setPassword(it) },
            name = stringResource(R.string.password), isPassword = true
        )
    }
}

@Composable
fun Buttons(navController: NavController, viewModel: LoginViewModel) {
    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                bottom = 15.dp,
                end = 15.dp,
                top = 5.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        FirstButton(
            name = stringResource(R.string.enter),
            state = viewModel.entrance.observeAsState(false),
            click = {
                viewModel.getLoginRequest()
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        SecondButton(
            name = stringResource(R.string.registration),
            navController = navController,
            route = Screen.RegistrationScreen.route
        )

        if (viewModel.mayGoToMain.observeAsState(false).value) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}