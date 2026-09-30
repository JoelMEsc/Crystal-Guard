package mx.tec.crystalguard.domain.people

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Educator(
    val id: Int,
    val name: String,
    val age: Int,
    val profileImage: ImageVector? = null,
    @param:DrawableRes val profileImageRes: Int? = null,
    val roleTitle: String = "Educador/a Principal",
    val groupIds: List<Int> = listOf(1),
    val students: List<Student> = emptyList(),
)
