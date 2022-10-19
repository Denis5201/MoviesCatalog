package com.example.moviecatalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen

private var login = mutableStateOf("")
private var password = mutableStateOf("")

@Composable
fun LoginScreen(navController: NavController) {
    Column {
        Logo()
        InputLines()
        Buttons(navController)
    }
}

@Preview(showBackground = true)
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

@Preview(showBackground = true)
@Composable
fun InputLines() {
    //var login by remember { mutableStateOf("") }
    //var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                top = 40.dp,
                end = 15.dp
            )
            .fillMaxWidth()
    ) {
        OneInputLine(state = login, name = "Логин", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = password, name = "Пароль", isPassword = true)
    }
}

//@Preview(showBackground = true)
@Composable
fun Buttons(navController: NavController) {
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
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            enabled = (login.value.isNotEmpty() && password.value.isNotEmpty())
        ) {
            Text(
                text = "Войти",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = if (login.value.isNotEmpty() && password.value.isNotEmpty()) {
                    MaterialTheme.colors.primaryVariant
                } else {
                    MaterialTheme.colors.primary
                }
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

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