package com.example.moviecatalog.screens.registration

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.moviecatalog.R
import com.example.moviecatalog.Screen
import com.example.moviecatalog.screens.*

@Composable
fun RegistrationScreen(
    navController: NavController,
    viewModel: RegistrationViewModel
) {
    Column {
        SmallLogo()
        Text(
            text = stringResource(R.string.registration),
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
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        )
    }
}

@Composable
fun RegistrationLines(viewModel: RegistrationViewModel) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(0.68f)
            .verticalScroll(rememberScrollState())
    ) {
        OneInputLine(
            state = viewModel.login.observeAsState(""), { viewModel.setLogin(it) },
            name = stringResource(R.string.login), isPassword = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(
            state = viewModel.mail.observeAsState(""), { viewModel.setMail(it) },
            name = stringResource(R.string.mail), isPassword = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(
            state = viewModel.name.observeAsState(""), { viewModel.setName(it) },
            name = stringResource(R.string.name), isPassword = false
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(
            state = viewModel.password.observeAsState(""), { viewModel.setPassword(it) },
            name = stringResource(R.string.password), isPassword = true
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OneInputLine(
            state = viewModel.confirmPassword.observeAsState(""),
            { viewModel.setConfirmPassword(it) },
            name = stringResource(R.string.confirm_password),
            isPassword = true
        )

        Spacer(modifier = Modifier.padding(10.dp))

        DateDialog(state = viewModel.date.observeAsState(""), valChange = { viewModel.setDate(it) })

        Spacer(modifier = Modifier.padding(10.dp))

        ChoosingGender(
            state = viewModel.selectGender.observeAsState(0),
            valChange = { viewModel.setSelectGender(it) })
    }
}

@Composable
fun RegButtons(navController: NavController, viewModel: RegistrationViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                bottom = 16.dp,
                end = 16.dp,
                top = 32.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        FirstButton(
            name = stringResource(R.string.set_registration),
            navController = navController,
            state = viewModel.registration.observeAsState(false),
            click = {
                if (viewModel.correctMail.value!!) {
                    if (viewModel.equalPasswords.value!!) {
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        Toast.makeText(context, R.string.passwords_not_equal, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, R.string.wrong_format_mail, Toast.LENGTH_LONG).show()
                }
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        SecondButton(
            name = stringResource(R.string.already_have_account),
            navController = navController,
            route = Screen.LoginScreen.route
        )
    }
}