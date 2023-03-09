package com.keithsmyth.ecommerce.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keithsmyth.ecommerce.domain.UserController
import com.keithsmyth.ecommerce.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userController: UserController,
) : ViewModel() {

    val viewState: StateFlow<AccountViewState> = userController.userFlow.map { userModel ->
        when (userModel) {
            UserState.Loading -> AccountViewState.Loading
            is UserState.Ready -> AccountViewState.Ready(
                username = userModel.username,
                showLogoutButton = !userModel.isGuest
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AccountViewState.Loading
    )

    fun logout() {
        viewModelScope.launch {
            userController.logout()
        }
    }

}

sealed class AccountViewState {
    object Loading : AccountViewState()
    data class Ready(
        val username: String,
        val showLogoutButton: Boolean,
    ) : AccountViewState()
}
