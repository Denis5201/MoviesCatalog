package com.example.moviecatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.moviecatalog.ui.theme.MovieCatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MovieCatalogTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Logo()
                        InputLines()
                        Buttons()
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Logo() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
        .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLines() {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(
            start = 15.dp,
            top = 40.dp,
            end = 15.dp
        )
        .fillMaxWidth()
    ) {
        OutlinedTextField(value = login,
            onValueChange = {login = it},
            placeholder = { Text(text = "Логин",
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.secondary,
                style = TextStyle(fontSize = 14.sp)
            )},
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary),
            modifier =  Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Пароль",
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.secondary,
                style = TextStyle(fontSize = 14.sp)
            )},
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                color = MaterialTheme.colors.primary),
            modifier =  Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
            ),
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Buttons() {
    Column(modifier = Modifier
        .padding(
            start = 15.dp,
            bottom = 15.dp,
            end = 15.dp
        )
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {  },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, MaterialTheme.colors.secondaryVariant),
            enabled = false
        ) {
            Text(text = "Войти",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {  },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(0.dp, MaterialTheme.colors.background),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background
            )
        ) {
            Text(text = "Регистрация",
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.ibm_plexsans)),
                fontSize = 20.sp,
                color = MaterialTheme.colors.primary
            )
        }
    }
}