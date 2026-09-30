package mx.tec.crystalguard.domain.people

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

data class Student(
    val id: Int,
    val name: String,
    var age: Int,
    val profileImage: ImageVector? = null,
    @param:DrawableRes val profileImageRes: Int? = null,
    val groupId: Int = 1,
    var familiarID: Int = 1,
    var educatorID: Int = 1,
)
