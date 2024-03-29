package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Favorites(
    @PrimaryKey val nameFavList: String,
    val publisher: String?,
    val designer: String,
    val id: String?
)