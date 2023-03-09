package com.keithsmyth.ecommerce.model

sealed class UserState {
    object Loading : UserState()
    data class Ready(val username: String, val isGuest: Boolean) : UserState()
}
