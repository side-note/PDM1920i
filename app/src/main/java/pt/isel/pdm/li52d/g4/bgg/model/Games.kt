package pt.isel.pdm.li52d.g4.bgg.model;

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.isel.pdm.li52d.g4.bgg.dto.ImageDto

@Entity
data class Games (
    @PrimaryKey var id: String,
    val images: ImageDto?,
    val artists: Array<String>?,
    val yearPublished: Int,
    val minPlayer: Int,
    val maxPlayer: Int,
    val minAge: Int,
    val description: String?,
    val primaryPublisher: String?,
    val avgUserRating: Double,
    val url: String?,
    val rulesUrl: String?

)
