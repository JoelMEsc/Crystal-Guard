package mx.tec.crystalguard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.EmojiNature
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import mx.tec.crystalguard.domain.Group

data class PersonInfo(
    val id: Int,
    val name: String,
    val role: String? = null,
    val icon: ImageVector = Icons.Filled.Person
)

data class GroupPeople(
    val educators: List<PersonInfo>,
    val students: List<PersonInfo>
)

class CrystalGuardRepository {
    private val groupData = listOf(
        Group(1, "Abejitas", "Gpo 302", Icons.Filled.EmojiNature, Color.Yellow),
        Group(2, "Estrellitas", "Gpo 101", Icons.Filled.Star, Color.Cyan),
        Group(3, "Conejitos", "Gpo 204", Icons.Filled.CrueltyFree, Color.Magenta)
    )

    private val peopleData = mapOf(
        1 to GroupPeople(
            educators = listOf(
                PersonInfo(1, "Ana Martínez", "Maestra Titular", Icons.Filled.Person)
            ),
            students = listOf(
                PersonInfo(101, "Leo Gómez", null, Icons.Filled.Person),
                PersonInfo(102, "Sofía Ramírez", null, Icons.Filled.Person),
                PersonInfo(103, "Mateo Jackson", null, Icons.Filled.Person),
                PersonInfo(104, "Valentina Chen", null, Icons.Filled.Person),
                PersonInfo(105, "Diego Torres", null, Icons.Filled.Person)
            )
        ),
        2 to GroupPeople(
            educators = listOf(
                PersonInfo(2, "Carlos Pérez", "Maestro Titular", Icons.Filled.Person)
            ),
            students = listOf(
                PersonInfo(201, "Lucía Morales", null, Icons.Filled.Person),
                PersonInfo(202, "Andrés Silva", null, Icons.Filled.Person),
                PersonInfo(203, "Camila Rojas", null, Icons.Filled.Person)
            )
        ),
        3 to GroupPeople(
            educators = listOf(
                PersonInfo(3, "María González", "Maestra Titular", Icons.Filled.Person)
            ),
            students = listOf(
                PersonInfo(301, "Gabriel Vargas", null, Icons.Filled.Person),
                PersonInfo(302, "Valeria Mendoza", null, Icons.Filled.Person)
            )
        )
    )

    fun getAll(): List<Group> = groupData
    fun getById(id: Int): Group? = groupData.firstOrNull { it.id == id }
    fun getPeopleForGroup(groupId: Int): GroupPeople? = peopleData[groupId]
}
