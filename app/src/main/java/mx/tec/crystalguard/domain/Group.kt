package mx.tec.crystalguard.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector // No deberia haber esto en el dominio, solo temporal

data class Group (
    val id: Int,
    val name: String,
    val group: String,
    val icon: ImageVector, // Temporal, deberia ser una referencia al OS y cargarse la imagen en otra funcion
    val color: Color
)