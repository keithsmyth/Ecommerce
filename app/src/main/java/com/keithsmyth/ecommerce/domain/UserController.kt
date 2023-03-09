package com.keithsmyth.ecommerce.domain

import com.keithsmyth.ecommerce.data.UserRepository
import com.keithsmyth.ecommerce.model.UserState
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

class UserController @Inject constructor(
    private val userRepository: UserRepository,
) {

    val userFlow: Flow<UserState> = userRepository.usernameFlow

    suspend fun login() {
        val randomUsername = UUID.randomUUID().toString().take(8)
        userRepository.updateUsername(randomUsername)
    }

    suspend fun logout() {
        userRepository.clearUsername()
    }

}
