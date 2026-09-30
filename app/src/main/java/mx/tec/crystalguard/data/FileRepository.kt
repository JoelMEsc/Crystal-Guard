package mx.tec.crystalguard.data

import mx.tec.crystalguard.domain.FileInfo

class FileRepository {

    private val booksMap = mapOf(
        1 to listOf(
            FileInfo(101, "Libro_Lectura_Inicial.pdf", "12.4 MB"),
            FileInfo(102, "Matemáticas_Divertidas_Vol1.pdf", "18.1 MB"),
            FileInfo(103, "Descubriendo_la_Naturaleza.pdf", "8.7 MB"),
        ),
        2 to listOf(
            FileInfo(104, "Cuentos_y_Leyendas_Infantiles.pdf", "15.0 MB"),
            FileInfo(105, "Mi_Primer_Cuaderno_de_Inglés.pdf", "9.3 MB"),
        ),
    )

    private val worksheetsMap = mapOf(
        1 to listOf(
            FileInfo(201, "Ejercicios_Grafomotricidad_Semana1.pdf", "1.8 MB"),
            FileInfo(202, "Ficha_Colorear_y_Trazar_Figuras.docx", "2.4 MB"),
            FileInfo(203, "Actividad_Conteo_Animales.pdf", "1.1 MB"),
        ),
        2 to listOf(
            FileInfo(204, "Práctica_Vocales_y_Consonantes.pdf", "2.0 MB"),
            FileInfo(205, "Recortables_Formas_Geométricas.pdf", "3.2 MB"),
        ),
    )

    fun getBooksForGroup(groupId: Int): List<FileInfo> {
        return booksMap[groupId] ?: booksMap[1] ?: emptyList()
    }

    fun getWorksheetsForGroup(groupId: Int): List<FileInfo> {
        return worksheetsMap[groupId] ?: worksheetsMap[1] ?: emptyList()
    }
}
