package com.example.moviecatalog.screens.registration

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    val status = viewModel.status.observeAsState()

    if (status.value!!.isError) {
        Toast.makeText(LocalContext.current, status.value!!.errorMessage, Toast.LENGTH_LONG).show()
        viewModel.setDefaultStatus()
    }
    if (!status.value!!.mayGoToMain && status.value!!.showMessage) {
        Toast.makeText(LocalContext.current, status.value!!.textMessage, Toast.LENGTH_SHORT).show()
        viewModel.setDefaultStatus()
    }
    if (status.value!!.mayGoToMain) {
        Toast.makeText(LocalContext.current, status.value!!.textMessage, Toast.LENGTH_SHORT).show()
        viewModel.setDefaultStatus()

        navController.navigate(Screen.MainScreen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Column {
        SmallLogo(viewModel)
        Text(
            text = stringResource(R.string.registration),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(start = 15.dp, top = 15.dp)
        )
        RegistrationLines(viewModel)
        RegButtons(navController, viewModel)
    }
    if (viewModel.start.observeAsState().value!! && !status.value!!.mayGoToMain) {
        viewModel.setStart(false)
    }
}

@Composable
fun SmallLogo(viewModel: RegistrationViewModel) {
    val start = viewModel.start.observeAsState(true)

    val animatedFloat: Float by animateFloatAsState(
        targetValue = if (start.value) 0.28f else 0.2f,
        animationSpec = tween(1000)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(animatedFloat)
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
            .fillMaxHeight(0.70f)
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
            state = viewModel.selectGender.observeAsState(2),
            valChange = { viewModel.setSelectGender(it) })
    }
}

@Composable
fun RegButtons(navController: NavController, viewModel: RegistrationViewModel) {
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
            state = viewModel.registration.observeAsState(false),
            click = {
                viewModel.getRegisterRequest()
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