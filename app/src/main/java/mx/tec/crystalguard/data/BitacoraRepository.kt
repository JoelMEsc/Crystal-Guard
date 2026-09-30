package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.DailyReport
import mx.tec.crystalguard.domain.EgresoData
import mx.tec.crystalguard.domain.EstadiaData
import mx.tec.crystalguard.domain.IngresoData

class BitacoraRepository {

    private val reports = mutableMapOf<String, DailyReport>()

    init {
        val keyLucas = "101_29 de Septiembre, 2026"
        reports[keyLucas] = DailyReport(
            studentId = 101,
            studentName = "Lucas Martínez López",
            groupId = 1,
            dateText = "29 de Septiembre, 2026",
            ingreso = IngresoData(
                horaEntrada = "08:30 AM",
                personaEntregaNombre = "Laura López (Mamá)",
                personalRecibeNombre = "Profra. María Elena García",
                estaEnfermo = false,
                dejoMedicamento = "No aplica",
                sintomasIngreso = "Sin síntomas",
                presentoLimpio = true,
                mochilaCompleta = true,
                buenEstadoFisico = true,
                lesionesFisicas = "Ninguna",
                observacionesPadres = "Trae su suéter azul etiquetado.",
            ),
            estadia = EstadiaData(
                durmioSiesta = true,
                tiempoSiestaMinutos = 45,
                comentariosSiesta = "Durmió tranquilo en su colchoneta.",
                conteoPipi = 3,
                conteoPopo = 1,
                obsAnimo = "Muy participativo en la clase de pintura.",
            ),
            egreso = EgresoData(
                buenEstadoFisicoSalida = true,
                horaSalida = "04:30 PM",
                personaRecibeNombre = "Laura López (Mamá)",
                personalEntregaNombre = "Profra. María Elena García",
                entregoLimpio = true,
                pertenenciasCompletas = true,
                observacionesSalida = "Se entrega sin novedades, día excelente.",
            ),
        )

        val keySofia = "102_29 de Septiembre, 2026"
        reports[keySofia] = DailyReport(
            studentId = 102,
            studentName = "Sofía Hernández Cruz",
            groupId = 1,
            dateText = "29 de Septiembre, 2026",
            ingreso = IngresoData(
                horaEntrada = "08:45 AM",
                personaEntregaNombre = "Carlos Hernández (Papá)",
                personalRecibeNombre = "Profra. María Elena García",
                estaEnfermo = false,
                dejoMedicamento = "No aplica",
            ),
        )
    }

    fun getReport(studentId: Int, studentName: String, groupId: Int, dateText: String): DailyReport {
        val key = "${studentId}_$dateText"
        return reports[key] ?: DailyReport(
            studentId = studentId,
            studentName = studentName,
            groupId = groupId,
            dateText = dateText,
        )
    }

    fun saveReport(report: DailyReport) {
        val key = "${report.studentId}_${report.dateText}"
        reports[key] = report
    }
}
