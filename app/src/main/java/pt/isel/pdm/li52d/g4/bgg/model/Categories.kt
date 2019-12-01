package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Entity

@Entity(tableName = "categories", primaryKeys = ["nameFavListC", "id"])
data class Categories(
    val nameFavListC: String,
    val id: String,
    val nameCategories: String,
    var checked: String
)