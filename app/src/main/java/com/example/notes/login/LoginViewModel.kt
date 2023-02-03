package com.example.notes.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository= AuthRepository()
):ViewModel(){

    val currentUser=repository.currentUser

    val hasUser:Boolean
    get()=repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())

    private set

    fun onUserNameChange(userName:String){
        loginUiState=loginUiState.copy(userName=userName)
    }

    fun onPasswordChange(password: String){
        loginUiState=loginUiState.copy(userName=password)
    }

    fun onUserNameChangeSignup(userName: String){
        loginUiState=loginUiState.copy(userNameSignup = userName)
    }

    fun onPasswordChangeSignup(password: String){
        loginUiState=loginUiState.copy(passwordSignup = password)
    }

    fun onConfirmPasswordChange(password:String){
        loginUiState=loginUiState.copy(confirmPasswordSignup = password)
    }

    private fun validateLoginForm() =
        loginUiState.userName.isNotBlank()&&
                loginUiState.password.isNotBlank()

    private fun validateSignupForm()=
        loginUiState.userNameSignup.isNotBlank()&&
                loginUiState.passwordSignup.isNotBlank()&&
                loginUiState.confirmPasswordSignup.isNotBlank()


    fun createUser(context:Context)=viewModelScope.launch {
        try{
            if(!validateSignupForm()){
                throw IllegalArgumentException("Email and Password cannot be empty0")
            }
            loginUiState=loginUiState.copy(isLoading = true)
            if(loginUiState.passwordSignup != loginUiState.confirmPasswordSignup){
                throw IllegalArgumentException("Password do not match")
            }
            loginUiState=loginUiState.copy(signUpError = null)

            repository.createUser(
                loginUiState.userNameSignup,
                loginUiState.passwordSignup
            ){
                isSuccessful->if(isSuccessful){
                    Toast.makeText(context,"Success Login",Toast.LENGTH_SHORT).show()
                    loginUiState=loginUiState.copy(isSuccessLogin = true)
            }else{
                Toast.makeText(context,"Failed Login",Toast.LENGTH_SHORT).show()
                loginUiState=loginUiState.copy(isSuccessLogin = false)
            }
            }

        }catch(e:Exception){
            loginUiState=loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState=loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context:Context)=viewModelScope.launch {
        try{
            if(!validateLoginForm()){
                throw IllegalArgumentException("Email and Password cannot be empty0")
            }
            loginUiState=loginUiState.copy(isLoading = true)
            loginUiState=loginUiState.copy(loginError =  null)

            repository.loginUser(
                loginUiState.userName,
                loginUiState.password
            ){
                    isSuccessful->if(isSuccessful){
                Toast.makeText(context,"Success Login",Toast.LENGTH_SHORT).show()
                loginUiState=loginUiState.copy(isSuccessLogin = true)
            }else{
                Toast.makeText(context,"Failed Login",Toast.LENGTH_SHORT).show()
                loginUiState=loginUiState.copy(isSuccessLogin = false)
            }
            }

        }catch(e:Exception){
            loginUiState=loginUiState.copy(loginError =  e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState=loginUiState.copy(isLoading = false)
        }
    }


}


data class LoginUiState(
    val userName:String="",
    val password:String="",
    val userNameSignup:String="",
    val passwordSignup:String="",
    val confirmPasswordSignup:String="",
    val isLoading:Boolean=false,
    val isSuccessLogin:Boolean=false,
    val signUpError:String?=null,
    val loginError: String?=null

)