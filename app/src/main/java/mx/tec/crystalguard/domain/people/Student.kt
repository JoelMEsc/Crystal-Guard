package mx.tec.crystalguard.domain.people

import androidx.compose.ui.graphics.vector.ImageVector // Tal como Group, también es temporal.

import mx.tec.crystalguard.domain.people.Familiar
import mx.tec.crystalguard.domain.people.Educator

import mx.tec.crystalguard.domain.Group

data class Student (
    // Personal Data
    val id:   Int,
    val name: String,
    var age:  Int,

    // Profile Info
    val profileImage: ImageVector,

    // Relationships
    var familiarID: Int,
    var educatorID: Int
) {}