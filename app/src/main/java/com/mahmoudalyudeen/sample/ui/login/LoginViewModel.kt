package com.mahmoudalyudeen.sample.ui.login

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    /** Model used to persist and validate the login form and a private backing property to prevent modification */
    private val _loginForm = LoginForm()
    val loginForm: LoginForm
        get() = _loginForm

    /** Example of using [map] with [LoginForm.username] to transform it to a personalized welcome message  */
    val loginText = map(_loginForm.username.valueLive) { "Welcome back $it" }

    fun onUsernameChanged(username: CharSequence) {
        _loginForm.username.value = username.toString()
    }

    fun onPasswordChanged(password: CharSequence) {
        _loginForm.password.value = password.toString()
    }

    fun onTermsOfServiceAgreeToggled() {
        _loginForm.agreeTos.apply { value = !value }
    }
}
