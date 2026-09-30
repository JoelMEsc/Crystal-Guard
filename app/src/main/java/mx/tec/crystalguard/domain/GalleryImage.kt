package mx.tec.crystalguard.domain

import androidx.annotation.DrawableRes

data class GalleryImage(
    val id: Int,
    val groupId: Int,
    val title: String,
    val date: String,
    @param:DrawableRes val imageRes: Int,
)
