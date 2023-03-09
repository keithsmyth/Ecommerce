package com.keithsmyth.ecommerce.ui.login

import app.cash.turbine.test
import com.keithsmyth.ecommerce.MainDispatcherRule
import com.keithsmyth.ecommerce.domain.UserController
import com.keithsmyth.ecommerce.model.UserState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userControllerMock: UserController

    @Test
    fun `when viewModel login called, calls login on UserController`() = runTest {
        // given
        val loginViewModel = LoginViewModel(userControllerMock)

        // when
        loginViewModel.login()

        // then
        verify(userControllerMock).login()
    }

    @Test
    fun `when viewModel login called, updates state to logging in`() = runTest {
        // given
        val loginViewModel = LoginViewModel(userControllerMock)

        loginViewModel.viewState.test {
            assertEquals("default to ready", LoginViewState.Ready, awaitItem())

            // when
            loginViewModel.login()

            // then
            assertEquals(LoginViewState.LoggingIn, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when full user added, state is updated to logged in`() = runTest {
        // given
        val userFlow = MutableSharedFlow<UserState>()
        whenever(userControllerMock.userFlow).thenReturn(userFlow)

        val loginViewModel = LoginViewModel(userControllerMock)

        loginViewModel.viewState.test {
            assertEquals("default to ready", LoginViewState.Ready, awaitItem())

            // when
            userFlow.emit(UserState.Ready("non-guest", false))

            // then
            assertEquals("full user update to logged in", LoginViewState.LoggedIn, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when guest added, state remains ready`() = runTest {
        // given
        val userFlow = MutableSharedFlow<UserState>()
        whenever(userControllerMock.userFlow).thenReturn(userFlow)

        val loginViewModel = LoginViewModel(userControllerMock)

        loginViewModel.viewState.test {
            assertEquals("default to ready", LoginViewState.Ready, awaitItem())

            // when
            userFlow.emit(UserState.Ready("guest", true))

            // then
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
