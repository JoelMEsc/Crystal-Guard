package mx.tec.crystalguard.domain.people

data class Familiar (
    val name: String,
    var age:  Int,

    val student: Student,
) {}