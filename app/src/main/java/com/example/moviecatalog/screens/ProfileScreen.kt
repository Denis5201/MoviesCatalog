package com.example.moviecatalog.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.skydoves.landscapist.glide.GlideImage
import java.util.*

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp,end = 16.dp)) {
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
        Text(text = "Тест", modifier = Modifier.padding(start = 16.dp), style = MaterialTheme.typography.h3)
    }
}

@Composable
fun ProfileLines(viewModel: ProfileViewModel) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        {
                _: DatePicker, year: Int, month:Int, dayOfMount: Int ->
            viewModel.setDate("$dayOfMount.${month+1}.$year")
        }, year, month, day
    )
    val selectGender by viewModel.selectGender.observeAsState(0)

    Text(text = "E-mail",
        modifier = Modifier.padding(top = 16.dp),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant
    )
    Spacer(modifier = Modifier.padding(4.dp))
    OneInputLine(state = viewModel.mail.observeAsState(""), {viewModel.setMail(it) },
        name = "E-mail", isPassword = false)

    Text(text = "Ссылка на аватарку",
        modifier = Modifier.padding(top = 10.dp),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant
    )
    Spacer(modifier = Modifier.padding(4.dp))
    OneInputLine(state = viewModel.avatar.observeAsState(""), {viewModel.setAvatar(it) },
        name = "Ссылка на аватарку", isPassword = false)

    Text(text = "Имя",
        modifier = Modifier.padding(top = 10.dp),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant
    )
    Spacer(modifier = Modifier.padding(4.dp))
    OneInputLine(state = viewModel.name.observeAsState(""), {viewModel.setName(it) },
        name = "Имя", isPassword = false)

    Text(text = "Дата рождения",
        modifier = Modifier.padding(top = 10.dp),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant
    )
    Spacer(modifier = Modifier.padding(4.dp))
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

    Text(text = "Пол",
        modifier = Modifier.padding(top = 10.dp),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { viewModel.setSelectGender(1) },
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
            onClick = { viewModel.setSelectGender(2) },
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

@Composable
fun ProfileButtons(navController: NavController, viewModel: ProfileViewModel) {
    val enable by viewModel.save.observeAsState(false)

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
                text = "Сохранить",
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
                text = "Выйти из аккаунта",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}