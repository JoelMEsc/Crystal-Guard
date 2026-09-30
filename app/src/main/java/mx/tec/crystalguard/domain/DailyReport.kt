package mx.tec.crystalguard.domain

enum class BitacoraSection(val label: String) {
    INGRESO("Ingreso (Entrada)"),
    ESTADIA("Estadía (Durante el día)"),
    EGRESO("Egreso (Salida)"),
}

enum class MealConsumption(val label: String) {
    NADA("Nada"),
    POCO("Poco"),
    TODO("Todo"),
    PIDIO_MAS("Pidió más"),
}

enum class ChildMood(val label: String) {
    FELIZ("Feliz"),
    TRISTE("Triste"),
    ENOJADO("Enojado"),
}

data class IngresoData(
    val horaEntrada: String = "08:30 AM",
    val personaEntregaNombre: String = "Laura López (Mamá)",
    val personalRecibeNombre: String = "Profra. María Elena García",
    val estaEnfermo: Boolean = false,
    val dejoMedicamento: String = "No aplica",
    val sintomasIngreso: String = "",
    val presentoLimpio: Boolean = true,
    val mochilaCompleta: Boolean = true,
    val buenEstadoFisico: Boolean = true,
    val lesionesFisicas: String = "Ninguna",
    val observacionesPadres: String = "",
)

data class EstadiaData(
    val desayuno: MealConsumption = MealConsumption.TODO,
    val colacion: MealConsumption = MealConsumption.TODO,
    val comida: MealConsumption = MealConsumption.POCO,
    val obsAlimentacion: String = "",
    val durmioSiesta: Boolean = true,
    val tiempoSiestaMinutos: Int = 45,
    val comentariosSiesta: String = "Durmió tranquilamente",
    val controlEsfinteresAviso: Boolean = true,
    val conteoPipi: Int = 3,
    val conteoPopo: Int = 1,
    val obsBano: String = "",
    val lloro: Boolean = false,
    val peleo: Boolean = false,
    val estadoAnimo: ChildMood = ChildMood.FELIZ,
    val participoActividades: Boolean = true,
    val obsAnimo: String = "",
    val tuvoAccidente: Boolean = false,
    val descAccidente: String = "",
    val folioAccidente: String = "",
    val tuvoProblemaSalud: Boolean = false,
    val descSalud: String = "",
    val atencionProporcionada: String = "",
    val obsGeneralesSalud: String = "",
)

data class EgresoData(
    val buenEstadoFisicoSalida: Boolean = true,
    val lesionesSalida: String = "Ninguna",
    val horaSalida: String = "04:30 PM",
    val personaRecibeNombre: String = "Laura López (Mamá)",
    val personalEntregaNombre: String = "Profra. María Elena García",
    val entregoLimpio: Boolean = true,
    val pertenenciasCompletas: Boolean = true,
    val observacionesSalida: String = "",
)

data class DailyReport(
    val studentId: Int,
    val studentName: String,
    val groupId: Int,
    val dateText: String = "29 de Septiembre, 2026",
    val ingreso: IngresoData = IngresoData(),
    val estadia: EstadiaData = EstadiaData(),
    val egreso: EgresoData = EgresoData(),
)
