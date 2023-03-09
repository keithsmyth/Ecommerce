package com.keithsmyth.ecommerce.ui.specials

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
class SpecialsViewModel @Inject constructor(
    userController: UserController,
) : ViewModel() {

    val viewState: StateFlow<SpecialsViewState> = userController.userFlow.map { userModel ->
        when (userModel) {
            UserState.Loading -> SpecialsViewState.Loading
            is UserState.Ready -> SpecialsViewState.Ready("foo")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SpecialsViewState.Loading
    )

}

sealed class SpecialsViewState {
    object Loading : SpecialsViewState()
    data class Ready(val foo: String) : SpecialsViewState()
}
