package com.keithsmyth.ecommerce.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.keithsmyth.ecommerce.model.UserState
import dagger.Lazy
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val encryptedSharedPreferences: Lazy<SharedPreferences>, // Lazy, to create off thread
) {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private val mutableUserFlow = MutableStateFlow<UserState>(UserState.Loading)

    val usernameFlow: StateFlow<UserState> = mutableUserFlow.asStateFlow()

    init {
        scope.launch(Dispatchers.IO) {
            val username = encryptedSharedPreferences.get().getString(KEY_USER_NAME, GUEST) ?: GUEST
            mutableUserFlow.emit(
                UserState.Ready(
                    username = username,
                    isGuest = username == GUEST,
                )
            )
        }
    }

    suspend fun updateUsername(username: String) {
        mutableUserFlow.emit(UserState.Loading)
        withContext(Dispatchers.IO) {
            simulateDelay()
            encryptedSharedPreferences.get().edit {
                putString(KEY_USER_NAME, username)
            }
            mutableUserFlow.value = UserState.Ready(
                username = username,
                isGuest = false,
            )
        }
    }

    suspend fun clearUsername() {
        mutableUserFlow.emit(UserState.Loading)
        withContext(Dispatchers.IO) {
            simulateDelay()
            encryptedSharedPreferences.get().edit {
                remove(KEY_USER_NAME)
            }
            mutableUserFlow.value = UserState.Ready(
                username = GUEST,
                isGuest = true,
            )
        }
    }

    private suspend fun simulateDelay() {
        delay(1000L)
    }

    companion object {
        private const val GUEST = "Guest"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
    }

}
