package mx.tec.crystalguard.ui.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

data class LoginUiState(
    val user: String = "",
    val password: String = ""
)

class LoginViewModel() : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsuarioChange(texto: String) {
        uiState = uiState.copy(user = texto)
    }

    fun onPasswordChange(texto: String) {
        uiState = uiState.copy(password = texto)
    }
}