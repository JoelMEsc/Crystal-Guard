package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.NotificationCategory
import mx.tec.crystalguard.domain.NotificationItem

class NotificationRepository {

    private val sampleNotifications = listOf(
        NotificationItem(
            id = 1,
            title = "Solicitud retiro de foto",
            person = "Marta Gómez (Mamá de Carlos)",
            group = "Abejitas (Gpo 302)",
            message = "Hola profe, agradecería que removiera la foto de la actividad de acuarelas por favor.",
            dateCategory = NotificationCategory.HOY,
            timeText = "Hace 20 min",
        ),
        NotificationItem(
            id = 2,
            title = "Respuesta a anuncio",
            person = "Carlos Hernández (Papá de Sofía)",
            group = "Abejitas (Gpo 302)",
            message = "Confirmado profe, Sofía llevará los materiales de plástico reciclables mañana.",
            dateCategory = NotificationCategory.HOY,
            timeText = "10:15 AM",
        ),
        NotificationItem(
            id = 3,
            title = "Confirmación de paseo escolar",
            person = "Laura López (Mamá de Lucas)",
            group = "Abejitas (Gpo 302)",
            message = "Ya firmé la autorización médica para la salida del próximo viernes.",
            dateCategory = NotificationCategory.ANTERIORES,
            timeText = "Ayer",
        ),
        NotificationItem(
            id = 4,
            title = "Consulta sobre bitácora",
            person = "Roberto Torres (Papá de Camila)",
            group = "Estrellitas (Gpo 101)",
            message = "Muchas gracias por registrar el horario de medicamento de Camila.",
            dateCategory = NotificationCategory.ANTERIORES,
            timeText = "Hace 2 días",
        ),
    )

    fun getTodayNotifications(): List<NotificationItem> {
        return sampleNotifications.filter { it.dateCategory == NotificationCategory.HOY }
    }

    fun getPreviousNotifications(): List<NotificationItem> {
        return sampleNotifications.filter { it.dateCategory == NotificationCategory.ANTERIORES }
    }
}
