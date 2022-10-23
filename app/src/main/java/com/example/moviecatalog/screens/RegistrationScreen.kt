package com.example.moviecatalog.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel
) {
    Column {
        SmallLogo()
        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(start = 15.dp, top = 15.dp)
        )
        RegistrationLines(viewModel)
        RegButtons(navController, viewModel)
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
fun RegistrationLines(viewModel: RegistrationViewModel) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        {
                _:DatePicker, year: Int, month:Int, dayOfMount: Int ->
            viewModel.setDate("$dayOfMount.${month+1}.$year")
        }, year, month, day
    )
    val selectGender by viewModel.selectGender.observeAsState(0)

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
        OneInputLine(state = viewModel.login.observeAsState(""), {viewModel.setLogin(it)},
            name = "Логин", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = viewModel.mail.observeAsState(""), {viewModel.setMail(it)},
            name = "E-mail", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = viewModel.name.observeAsState(""), {viewModel.setName(it)},
            name = "Имя", isPassword = false)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = viewModel.password.observeAsState(""), {viewModel.setPassword(it)},
            name = "Пароль", isPassword = true)

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(state = viewModel.confirmPassword.observeAsState(""), {viewModel.setConfirmPassword(it)},
            name = "Подтвердите пароль", isPassword = true)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = viewModel.date.observeAsState("").value,
            onValueChange = { viewModel.setDate(it) },
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
                onClick = {viewModel.setSelectGender(1)},
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier.fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectGender != 1) {
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
                    color = if (selectGender == 1) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        MaterialTheme.colors.primary
                    }
                )
            }
            Button(
                onClick = {viewModel.setSelectGender(2)},
                border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (selectGender != 2) {
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
                    color = if (selectGender == 2) {
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
fun RegButtons(navController: NavController, viewModel: RegistrationViewModel) {
    val enable by viewModel.registration.observeAsState(false)

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
                text = "Зарегистрироваться",
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