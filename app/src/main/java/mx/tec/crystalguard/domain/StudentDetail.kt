package mx.tec.crystalguard.domain

import mx.tec.crystalguard.domain.people.Student

data class StudentDetail(
    val student: Student,
    val groupName: String = "Abejitas (Gpo 302)",
    val tutorName: String = "Laura López Fernández",
    val tutorContact: String = "+52 55 1234 5678",
    val notes: List<StudentNote> = emptyList(),
    val attachedFiles: List<FileInfo> = emptyList(),
)
