package mx.tec.crystalguard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.tec.crystalguard.ui.screens.LoginScreen
import mx.tec.crystalguard.ui.state.LoginUiState
import mx.tec.crystalguard.ui.state.LoginViewModel

enum class Role {
    EDUCATOR, TUTOR, ANONYMOUS
}

data class UserUiState(
    val role: Role = Role.ANONYMOUS
)

class UserViewModel() : ViewModel() {
    var uiState by mutableStateOf(UserUiState())

    fun onLogin(text: String) {
        uiState = uiState.copy(role = if (text == "educator") Role.EDUCATOR else Role.TUTOR)
    }
}

@Composable
fun CrystalGuardApp() {
    val userViewModel: UserViewModel = viewModel()

    when (val actual = userViewModel.uiState.role) {
        Role.ANONYMOUS -> {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                uiState = loginViewModel.uiState,
                onUsuarioChange = loginViewModel::onUsuarioChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onEnviar = {userViewModel.onLogin("educator")}
            )
        }

        Role.EDUCATOR -> {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                uiState = LoginUiState(user = "Eduardo Castillo", password = "secreta123"),
                onUsuarioChange = {}, onPasswordChange = {}, onEnviar = {}
            )
        }

        Role.TUTOR -> {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                uiState = LoginUiState(user = "Eduardo Castillo", password = "secreta123"),
                onUsuarioChange = {}, onPasswordChange = {}, onEnviar = {}
            )
        }
    }
}