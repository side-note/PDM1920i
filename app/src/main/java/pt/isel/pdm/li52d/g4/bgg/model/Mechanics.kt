package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Entity

@Entity (tableName = "mechanics", primaryKeys = ["nameFavListM", "id"])
data class Mechanics(
    val nameFavListM: String,
    val id: String,
    val nameMechanics: String,
    var checked: String
)