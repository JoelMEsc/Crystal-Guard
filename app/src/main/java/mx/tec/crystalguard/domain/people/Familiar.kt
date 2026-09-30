package mx.tec.crystalguard.domain.people

data class Familiar(
    val id: Int = 1,
    val name: String,
    var age: Int,
    val student: Student? = null,
)
