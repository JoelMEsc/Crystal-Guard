package mx.tec.crystalguard.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CrueltyFree
import androidx.compose.material.icons.filled.EmojiNature
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import mx.tec.crystalguard.domain.Group

class CrystalGuardRepository {
    private val groupData = listOf(
        Group(1, "Abejitas", "Gpo 302", Icons.Filled.EmojiNature, Color.Yellow),
        Group(2, "Estrellitas", "Gpo 101", Icons.Filled.Star, Color.Cyan),
        Group(3, "Conejitos", "Gpo 204", Icons.Filled.CrueltyFree, Color.Magenta)
    )

    fun getAll(): List<Group> = groupData
    fun getById(id: Int): Group? = groupData.firstOrNull { it.id == id }
}