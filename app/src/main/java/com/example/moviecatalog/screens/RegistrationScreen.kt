package com.example.moviecatalog.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import java.util.*

private var login = mutableStateOf("")
private var mail = mutableStateOf("")
private var name = mutableStateOf("")
private var password = mutableStateOf("")
private var confirm = mutableStateOf("")
private var date = mutableStateOf("")

private var gender = listOf(0, 1, 2)
private var select = mutableStateOf(gender[0])

@Composable
fun RegistrationScreen(navController: NavController) {
    Column {
        SmallLogo()
        Text(
            text = "Регистрация",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(start = 15.dp, top = 15.dp)
        )
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
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        {
                _:DatePicker, year: Int, month:Int, dayOfMount: Int ->
            date.value = "$dayOfMount.${month+1}.$year"
        }, year, month, day
    )
    Column(
        modifier = Modifier
            .padding(
                start = 10.dp,
                top = 20.dp,
                end = 15.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(0.72f)
            .verticalScroll(rememberScrollState())
    ) {
        OneInputLine(state = login, name = "Логин", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = mail, name = "E-mail", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = name, name = "Имя", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = password, name = "Пароль", isPassword = true)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = confirm, name = "Подтвердите пароль", isPassword = true)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = date.value,
            onValueChange = { date.value = it },
            trailingIcon = {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.date_icon),
                    contentDescription = "Календарь",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            },
            readOnly = true,
            placeholder = {
                Text(
                    text = "Дата рождения",
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body1
                )
            },
            textStyle = TextStyle(
                fontFamily = MaterialTheme.typography.body1.fontFamily,
                color = MaterialTheme.colors.primary
            ),
            modifier = Modifier
                .clickable {
                    datePickerDialog.show()
                }
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant,
                disabledBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = MaterialTheme.shapes.large,
            enabled = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(modifier = Modifier.fillMaxWidth()){
            Button(
                onClick = {select.value = 1},
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (select.value != gender[1]) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.primary
                    }
                ),
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
            ) {
                Text(
                    text = "Мужчина",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                    color = if (select.value == gender[1]) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.primary
                    }
                )
            }
            Button(
                onClick = {select.value = 2},
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (select.value != gender[2]) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.primary
                    }
                ),
                shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
            ) {
                Text(
                    text = "Женщина",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                    color = if (select.value == gender[2]) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.primary
                    }
                )
            }
        }

    }
}

@Composable
fun RegButtons(navController: NavController) {
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
            enabled = (login.value.isNotEmpty() && mail.value.isNotEmpty()
                    && name.value.isNotEmpty() && password.value.isNotEmpty()
                    && confirm.value.isNotEmpty() && date.value.isNotEmpty() && select.value !=0)
        ) {
            Text(
                text = "Зарегистрироваться",
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
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}