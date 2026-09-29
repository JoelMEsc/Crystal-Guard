package mx.tec.crystalguard.ui.navigation

object Route {
    const val EDUCATOR_DASHBOARD = "educatorDashboard"
    const val EDUCATOR_CALENDAR = "educatorCalendar"
    const val EDUCATOR_NOTIFICATIONS = "educatorNotificacions"

    const val GROUP_DETAIL = "detail/{groupId}"

    fun detail(id: Int) = "detail/$id"
}
