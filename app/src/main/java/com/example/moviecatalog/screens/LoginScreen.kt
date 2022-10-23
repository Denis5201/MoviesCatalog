package com.example.moviecatalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen

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
            contentDescription = "logo",
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
        OneInputLine(state = viewModel.name.observeAsState(""), {viewModel.setName(it) },
            name = "Логин", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = viewModel.password.observeAsState(""), {viewModel.setPassword(it) },
            name = "Пароль", isPassword = true)
    }
}

//@Preview(showBackground = true)
@Composable
fun Buttons(navController: NavController, viewModel: LoginViewModel) {
    val enable by viewModel.entrance.observeAsState(false)

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
        Button(
            onClick = {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            border = if (enable) null else BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            enabled = enable
        ) {
            Text(
                text = "Войти",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = if (enable) {
                    MaterialTheme.colors.primaryVariant
                } else {
                    MaterialTheme.colors.primary
                }
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = {
                navController.navigate(Screen.RegistrationScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(0.dp, MaterialTheme.colors.background),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            )
        ) {
            Text(
                text = "Регистрация",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}