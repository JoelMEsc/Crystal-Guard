package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.CalendarActivity
import mx.tec.crystalguard.domain.MonthDay
import mx.tec.crystalguard.domain.WeekDay
import mx.tec.crystalguard.domain.WeekInfo

data class MonthCalendarInfo(
    val monthYearTitle: String,
    val days: List<MonthDay>,
)

class CalendarRepository {

    private val availableWeeks = listOf(
        WeekInfo(
            headerTitle = "Septiembre 21 - 27",
            days = listOf(
                WeekDay(letter = "L", number = 21, isSelected = true),
                WeekDay(letter = "M", number = 22, isSelected = false),
                WeekDay(letter = "M", number = 23, isSelected = false),
                WeekDay(letter = "J", number = 24, isSelected = false),
                WeekDay(letter = "V", number = 25, isSelected = false),
                WeekDay(letter = "S", number = 26, isSelected = false),
                WeekDay(letter = "D", number = 27, isSelected = false),
            ),
        ),
        WeekInfo(
            headerTitle = "Septiembre 28 - Octubre 4",
            days = listOf(
                WeekDay(letter = "L", number = 28, isSelected = false),
                WeekDay(letter = "M", number = 29, isSelected = true),
                WeekDay(letter = "M", number = 30, isSelected = false),
                WeekDay(letter = "J", number = 1, isSelected = false),
                WeekDay(letter = "V", number = 2, isSelected = false),
                WeekDay(letter = "S", number = 3, isSelected = false),
                WeekDay(letter = "D", number = 4, isSelected = false),
            ),
        ),
        WeekInfo(
            headerTitle = "Octubre 5 - 11",
            days = listOf(
                WeekDay(letter = "L", number = 5, isSelected = true),
                WeekDay(letter = "M", number = 6, isSelected = false),
                WeekDay(letter = "M", number = 7, isSelected = false),
                WeekDay(letter = "J", number = 8, isSelected = false),
                WeekDay(letter = "V", number = 9, isSelected = false),
                WeekDay(letter = "S", number = 10, isSelected = false),
                WeekDay(letter = "D", number = 11, isSelected = false),
            ),
        ),
    )

    private val availableMonths = listOf(
        MonthCalendarInfo(
            monthYearTitle = "Agosto 2026",
            days = buildList {
                for (i in 27..31) add(MonthDay(dayNumber = i, isCurrentMonth = false))
                for (i in 1..31) add(MonthDay(dayNumber = i, isCurrentMonth = true, isSelected = (i == 15)))
                for (i in 1..6) add(MonthDay(dayNumber = i, isCurrentMonth = false))
            },
        ),
        MonthCalendarInfo(
            monthYearTitle = "Septiembre 2026",
            days = buildList {
                for (i in 28..31) add(MonthDay(dayNumber = i, isCurrentMonth = false))
                for (i in 1..30) add(MonthDay(dayNumber = i, isCurrentMonth = true, isSelected = (i == 29)))
                for (i in 1..8) add(MonthDay(dayNumber = i, isCurrentMonth = false))
            },
        ),
        MonthCalendarInfo(
            monthYearTitle = "Octubre 2026",
            days = buildList {
                for (i in 28..30) add(MonthDay(dayNumber = i, isCurrentMonth = false))
                for (i in 1..31) add(MonthDay(dayNumber = i, isCurrentMonth = true, isSelected = (i == 5)))
                for (i in 1..8) add(MonthDay(dayNumber = i, isCurrentMonth = false))
            },
        ),
    )

    private val allActivities = mapOf(
        21 to listOf(
            CalendarActivity(
                groupId = 1,
                groupName = "Abejitas",
                groupNumber = "Gpo 302",
                title = "Evaluación Diagnóstica",
                description = "Pruebas iniciales de desarrollo motriz y sociabilidad.",
                startTime = "09:00 AM",
                endTime = "10:30 AM",
            ),
        ),
        22 to listOf(
            CalendarActivity(
                groupId = 2,
                groupName = "Estrellitas",
                groupNumber = "Gpo 101",
                title = "Taller de Psicomotricidad",
                description = "Ejercicios de equilibrio y coordinación corporal.",
                startTime = "10:00 AM",
                endTime = "11:30 AM",
            ),
        ),
        28 to listOf(
            CalendarActivity(
                groupId = 1,
                groupName = "Abejitas",
                groupNumber = "Gpo 302",
                title = "Sesión de Bienvenida",
                description = "Presentación de la semana y dinámicas grupales al aire libre.",
                startTime = "08:30 AM",
                endTime = "10:00 AM",
            ),
        ),
        29 to listOf(
            CalendarActivity(
                groupId = 1,
                groupName = "Abejitas",
                groupNumber = "Gpo 302",
                title = "Taller de Pintura y Creatividad",
                description = "Actividad sensorial con pintura lavable para estimular la motricidad fina.",
                startTime = "09:00 AM",
                endTime = "10:30 AM",
            ),
            CalendarActivity(
                groupId = 2,
                groupName = "Estrellitas",
                groupNumber = "Gpo 101",
                title = "Cuentacuentos Interactivo",
                description = "Lectura guiada con marionetas para fortalecer el vocabulario y comprensión.",
                startTime = "11:00 AM",
                endTime = "12:15 PM",
            ),
        ),
        30 to listOf(
            CalendarActivity(
                groupId = 3,
                groupName = "Conejitos",
                groupNumber = "Gpo 204",
                title = "Juegos de Coordinación Motriz",
                description = "Circuitos divertidos de obstáculos suaves y psicomotricidad.",
                startTime = "10:00 AM",
                endTime = "11:30 AM",
            ),
        ),
        1 to listOf(
            CalendarActivity(
                groupId = 2,
                groupName = "Estrellitas",
                groupNumber = "Gpo 101",
                title = "Taller de Música y Ritmo",
                description = "Exploración de instrumentos rítmicos de madera y canciones.",
                startTime = "09:30 AM",
                endTime = "11:00 AM",
            ),
        ),
        2 to listOf(
            CalendarActivity(
                groupId = 1,
                groupName = "Abejitas",
                groupNumber = "Gpo 302",
                title = "Manualidades de Cierre de Semana",
                description = "Elaboración de recuerdos con plastilina moldeable de colores.",
                startTime = "09:00 AM",
                endTime = "10:30 AM",
            ),
        ),
        5 to listOf(
            CalendarActivity(
                groupId = 3,
                groupName = "Conejitos",
                groupNumber = "Gpo 204",
                title = "Inicio del Proyecto de Naturaleza",
                description = "Observación de plantas y flores en el jardín escolar.",
                startTime = "09:00 AM",
                endTime = "10:30 AM",
            ),
        ),
        6 to listOf(
            CalendarActivity(
                groupId = 1,
                groupName = "Abejitas",
                groupNumber = "Gpo 302",
                title = "Experimentos sencillos de Ciencia",
                description = "Demostraciones visuales con agua y colores.",
                startTime = "11:00 AM",
                endTime = "12:00 PM",
            ),
        ),
    )

    fun getAvailableWeeks(): List<WeekInfo> = availableWeeks

    fun getAvailableMonths(): List<MonthCalendarInfo> = availableMonths

    fun getActivitiesForDay(dayNumber: Int): List<CalendarActivity> {
        return allActivities[dayNumber] ?: emptyList()
    }

    fun getActivitiesForGroupAndDay(groupId: Int, dayNumber: Int): List<CalendarActivity> {
        return (allActivities[dayNumber] ?: emptyList()).filter { it.groupId == groupId }
    }
}
