package com.keithsmyth.ecommerce.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keithsmyth.ecommerce.domain.UserController
import com.keithsmyth.ecommerce.model.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    userController: UserController,
) : ViewModel() {

    val viewState: StateFlow<DashboardViewState> = userController.userFlow.map { userModel ->
        when (userModel) {
            UserState.Loading -> DashboardViewState.Loading
            is UserState.Ready -> DashboardViewState.Ready(
                username = userModel.username,
                showLoginButton = userModel.isGuest,
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = DashboardViewState.Loading
    )

}

sealed class DashboardViewState {

    object Loading : DashboardViewState()

    data class Ready(
        val username: String,
        val showLoginButton: Boolean,
    ) : DashboardViewState()

}
