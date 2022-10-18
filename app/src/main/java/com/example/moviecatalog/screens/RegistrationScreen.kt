package com.example.moviecatalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen

private var login = mutableStateOf("")
private var password = mutableStateOf("")

@Composable
fun RegistrationScreen(navController: NavController) {
    Column {
        SmallLogo()
        RegistrationLines()
        RegButtons(navController)
    }
}

@Composable
fun SmallLogo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
}

@Composable
fun RegistrationLines() {
    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                top = 20.dp,
                end = 15.dp
            )
            .fillMaxWidth()
    ) {
        Text(
            text = "Регистрация",
            fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
            fontWeight = FontWeight.W700,
            letterSpacing = 1.sp,
            color = MaterialTheme.colors.primary,
            style = TextStyle(fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = login.value,
            onValueChange = { login.value = it },
            placeholder = {
                Text(
                    text = "Логин",
                    fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = {
                Text(
                    text = "E-mail",
                    fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp),
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = {
                Text(
                    text = "Имя",
                    fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp),
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = {
                Text(
                    text = "Пароль",
                    fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = {
                Text(
                    text = "Подтвердите пароль",
                    fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                    color = MaterialTheme.colors.secondary,
                    style = TextStyle(fontSize = 14.sp)
                )
            },
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun RegButtons(navController: NavController) {
    Column(
        modifier = Modifier
            .padding(
                start = 15.dp,
                bottom = 15.dp,
                end = 15.dp
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
                text = "Зарегистрироваться",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                fontSize = 20.sp,
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
                navController.navigate(Screen.LoginScreen.route) {
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
                text = "У меня уже есть аккаунт",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
}