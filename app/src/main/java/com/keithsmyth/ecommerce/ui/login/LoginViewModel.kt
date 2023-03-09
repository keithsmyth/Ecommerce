package com.keithsmyth.ecommerce.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keithsmyth.ecommerce.business.UserController
import com.keithsmyth.ecommerce.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userController: UserController,
) : ViewModel() {

    private val mutableViewState = MutableStateFlow<LoginViewState>(LoginViewState.Ready)

    val viewState: StateFlow<LoginViewState> = mutableViewState.asStateFlow()

    init {
        viewModelScope.launch {
            userController.userFlow
                .filterIsInstance<UserState.Ready>()
                .filterNot { it.isGuest }
                .collect { mutableViewState.emit(LoginViewState.LoggedIn) }
        }
    }

    fun login() {
        viewModelScope.launch {
            mutableViewState.emit(LoginViewState.LoggingIn)
            userController.login()
        }
    }

}

sealed class LoginViewState {
    object Ready : LoginViewState()
    object LoggingIn : LoginViewState()
    object LoggedIn : LoginViewState()
}

