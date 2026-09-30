package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.people.Educator
import mx.tec.crystalguard.domain.people.Student

class PeopleRepository {

    private val educators = listOf(
        Educator(
            id = 1,
            name = "Profra. María Elena García",
            age = 34,
            roleTitle = "Educadora Titular",
            groupIds = listOf(1),
        ),
        Educator(
            id = 2,
            name = "Prof. Roberto Fernández",
            age = 41,
            roleTitle = "Educador Titular",
            groupIds = listOf(2),
        ),
        Educator(
            id = 3,
            name = "Profra. Ana Sofia Castro",
            age = 29,
            roleTitle = "Educadora Titular",
            groupIds = listOf(3),
        ),
    )

    private val students = listOf(
        Student(id = 101, name = "Lucas Martínez López", age = 5, groupId = 1),
        Student(id = 102, name = "Sofía Hernández Cruz", age = 4, groupId = 1),
        Student(id = 103, name = "Mateo Gómez Ramírez", age = 5, groupId = 1),
        Student(id = 104, name = "Emma Valentina Ruiz", age = 4, groupId = 1),
        Student(id = 201, name = "Camila Torres Morales", age = 5, groupId = 2),
        Student(id = 202, name = "Diego Navarro Vargas", age = 4, groupId = 2),
        Student(id = 203, name = "Valeria Mendoza Soto", age = 5, groupId = 2),
        Student(id = 301, name = "Santiago Delgado Peña", age = 4, groupId = 3),
        Student(id = 302, name = "Isabella Flores Ríos", age = 5, groupId = 3),
    )

    fun getEducatorForGroup(groupId: Int): Educator? {
        return educators.firstOrNull { groupId in it.groupIds } ?: educators.firstOrNull()
    }

    fun getStudentsForGroup(groupId: Int): List<Student> {
        return students.filter { it.groupId == groupId }
    }
}
