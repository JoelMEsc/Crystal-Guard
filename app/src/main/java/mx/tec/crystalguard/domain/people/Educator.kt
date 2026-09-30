package mx.tec.crystalguard.domain.people

data class Educator (
    // Personal Data
    val id:   Int,
    val name: String,
    val age:  Int,

    //
    val students: List<Student>

    // Relationships

) {}