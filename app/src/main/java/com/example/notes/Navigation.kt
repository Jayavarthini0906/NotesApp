package com.example.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.home.Home
import com.example.notes.login.LoginScreen
import com.example.notes.login.LoginViewModel
import com.example.notes.login.SignupScreen

enum class LoginRoutes{
    Signup,
    SignIn
}

enum class HomeRoutes{
    Home,
    Detail
}

@Composable
fun Navigation(
    navController: NavHostController= rememberNavController(),
    loginViewModel: LoginViewModel
){
    NavHost(navController = navController, startDestination =LoginRoutes.SignIn.name){
        composable(route = LoginRoutes.SignIn.name){
            LoginScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name){
                    launchSingleTop=true
                    popUpTo(route = LoginRoutes.SignIn.name){
                        inclusive=true
                    }
                }
            }){
                navController.navigate(LoginRoutes.Signup.name){
                    launchSingleTop=true
                    popUpTo(LoginRoutes.SignIn.name){
                        inclusive=true
                    }
                }
            }
        }

        composable(route=LoginRoutes.SignIn.name){
            SignupScreen(onNavToHomePage = {  navController.navigate(HomeRoutes.Home.name){
                popUpTo(LoginRoutes.Signup.name){
                    inclusive=true
                }
            }}) {
               navController.navigate(LoginRoutes.SignIn.name)
            }
        }

        composable(route= HomeRoutes.Home.name){
            Home()
        }
    }

}
