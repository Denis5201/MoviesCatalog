package com.example.moviecatalog.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun OneInputLine(state: MutableState<String>, name: String, isPassword: Boolean) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
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
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondaryVariant,
            unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
        ),
        shape = MaterialTheme.shapes.large,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None
    )
}