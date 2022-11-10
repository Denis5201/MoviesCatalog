package com.example.moviecatalog.screens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import java.util.*

@Composable
fun OneInputLine(
    state: State<String>,
    valChange: (String) -> Unit,
    name: String,
    isPassword: Boolean
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { valChange(it) },
        placeholder = {
            Text(
                text = name,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1
            )
        },
        textStyle = TextStyle(
            fontFamily = MaterialTheme.typography.body1.fontFamily,
            color = MaterialTheme.colors.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondaryVariant,
            unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
        ),
        shape = MaterialTheme.shapes.medium,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
}

@Composable
fun DateDialog(
    state: State<String>,
    valChange: (Date) -> Unit,
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, year: Int, month: Int, dayOfMount: Int ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMount)
            valChange(date.time)
        }, year, month, day
    )

    OutlinedTextField(
        value = state.value,
        onValueChange = { },
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.date_icon),
                contentDescription = stringResource(R.string.calendar_description),
                tint = MaterialTheme.colors.secondaryVariant
            )
        },
        readOnly = true,
        placeholder = {
            Text(
                text = stringResource(R.string.date_birthday),
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
            .fillMaxWidth()
            .height(52.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondaryVariant,
            unfocusedBorderColor = MaterialTheme.colors.secondaryVariant,
            disabledBorderColor = MaterialTheme.colors.secondaryVariant
        ),
        shape = MaterialTheme.shapes.medium,
        enabled = false
    )
}

@Composable
fun ChoosingGender(state: State<Int>, valChange: (Int) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)) {
        Button(
            onClick = { valChange(1) },
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            modifier = Modifier.fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (state.value != 1) {
                    MaterialTheme.colors.background
                } else {
                    MaterialTheme.colors.primary
                }
            ),
            shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.man),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = if (state.value == 1) {
                    MaterialTheme.colors.primaryVariant
                } else {
                    MaterialTheme.colors.primary
                }
            )
        }
        Button(
            onClick = { valChange(0) },
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (state.value != 0) {
                    MaterialTheme.colors.background
                } else {
                    MaterialTheme.colors.primary
                }
            ),
            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
        ) {
            Text(
                text = stringResource(R.string.woman),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                color = if (state.value == 0) {
                    MaterialTheme.colors.primaryVariant
                } else {
                    MaterialTheme.colors.primary
                }
            )
        }
    }
}

@Composable
fun FirstButton(
    name: String,
    state: State<Boolean>,
    click: () -> Unit
) {
    Button(
        onClick = { click() },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        border = if (state.value) {
            null
        } else {
            BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant)
        },
        enabled = state.value
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            color = if (state.value) {
                MaterialTheme.colors.primaryVariant
            } else {
                MaterialTheme.colors.primary
            }
        )
    }
}

@Composable
fun SecondButton(
    name: String,
    navController: NavController,
    route: String
) {
    Button(
        onClick = {
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        border = BorderStroke(0.dp, MaterialTheme.colors.background),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        )
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
fun LoadingProgress() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colors.primary)
    }
}

@Composable
fun NameDetail(idString: Int) {
    Text(
        text = stringResource(idString),
        style = MaterialTheme.typography.h6,
        color = colorResource(R.color.about),
        modifier = Modifier.width(110.dp)
    )
}