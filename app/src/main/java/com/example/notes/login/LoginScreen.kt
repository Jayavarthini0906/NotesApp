package com.example.notes.login

import android.telephony.SignalStrengthUpdateRequest
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.ui.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    OnNavToSignUpPage: () -> Unit,

) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )
        if (isError) {
            Text(
                text = loginUiState?.loginError ?: "Unknown error",
                color = Color.Red
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value =loginUiState?.userName?:"",
            onValueChange = {
                loginViewModel?.onUserNameChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription= null
                )
            },
            label={
                Text(text = "Email")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError=isError


        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value =loginUiState?.password?:"",
            onValueChange = {
                loginViewModel?.onPasswordChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription= null
                )
            },
            label={
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError=isError


        )

        Button(onClick = {loginViewModel?.loginUser(context) }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Don't have an Account?", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = {OnNavToSignUpPage.invoke() }) {
                Text(text = "SignUp",
                color = Color.Green)
            }
        }

        if(loginUiState?.isLoading==true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if(loginViewModel?.hasUser==true){
                onNavToHomePage.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    OnNavToLoginPage: () -> Unit,

    ) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Signup",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary
        )
        if (isError) {
            Text(
                text = loginUiState?.signUpError?: "Unknown error",
                color = Color.Red
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value =loginUiState?.userNameSignup?:"",
            onValueChange = {
                loginViewModel?.onPasswordChangeSignup(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription= null
                )
            },
            label={
                Text(text = "Email")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError=isError


        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value =loginUiState?.passwordSignup?:"",
            onValueChange = {
                loginViewModel?.onPasswordChangeSignup(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription= null
                )
            },
            label={
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError=isError
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value =loginUiState?.confirmPasswordSignup?:"",
            onValueChange = {
                loginViewModel?.onConfirmPasswordChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription= null
                )
            },
            label={
                Text(text = "Confirm Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError=isError
        )

        Button(onClick = {loginViewModel?.loginUser(context) }) {
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Already have an Account?", color = Color.Gray)
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = {OnNavToLoginPage.invoke() }) {
                Text(text = "SignIn",
                    color = Color.Green)
            }
        }

        if(loginUiState?.isLoading==true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if(loginViewModel?.hasUser==true){
                onNavToHomePage.invoke()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun prevLoginScreen(){
    NotesTheme() {
        LoginScreen(
            onNavToHomePage = { /*TODO*/ },
            OnNavToSignUpPage = {

            },
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun prevSignupScreen (){
    NotesTheme() {
        SignupScreen(onNavToHomePage = { /*TODO*/ }) {
            
        }
    }
}
