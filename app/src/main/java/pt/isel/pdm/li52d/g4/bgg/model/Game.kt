package pt.isel.pdm.li52d.g4.bgg.model;

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.isel.pdm.li52d.g4.bgg.dto.ImageDto

@Entity
data class Game (
    @PrimaryKey var id: String,
    val name: String?,
    val yearPublished: Int,
    val minPlayer: Int,
    val maxPlayer: Int,
    val minAge: Int,
    val description: String?,
    val primaryPublisher: String?
)
