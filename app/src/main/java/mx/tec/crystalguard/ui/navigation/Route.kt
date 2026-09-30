package mx.tec.crystalguard.ui.navigation

object Route {
    const val EDUCATOR_DASHBOARD = "educatorDashboard"
    const val EDUCATOR_CALENDAR = "educatorCalendar"
    const val EDUCATOR_NOTIFICATIONS = "educatorNotificacions"

    const val GROUP_DETAIL = "detail/{groupId}"

    const val CLASS_MATERIAL = "classMaterial/{groupId}"
    const val ATTENDANCE = "attendance/{groupId}"
    const val ANNOUNCEMENTS = "announcements/{groupId}"
    const val BITACORA = "bitacora/{groupId}"
    const val GALERIA = "galery/{groupId}"
    const val PEOPLE = "people/{groupId}"
    const val CALENDAR = "calendar/{groupId}"

    const val STUDENT_DETAIL = "studentDetail/{studentId}"

    fun detail(id: Int) = "detail/$id"

    fun classMaterial(groupId: Int) = "classMaterial/$groupId"
    fun attendance(groupId: Int) = "attendance/$groupId"
    fun announcements(groupId: Int) = "announcements/$groupId"
    fun bitacora(groupId: Int) = "bitacora/$groupId"
    fun galery(groupId: Int) = "galery/$groupId"
    fun people(groupId: Int) = "people/$groupId"
    fun calendar(groupId: Int) = "calendar/$groupId"

    fun studentDetail(studentId: Int) = "studentDetail/$studentId"
}
