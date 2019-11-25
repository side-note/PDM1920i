package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "customlist_game_join",
        primaryKeys = arrayOf("customlistName", "gameId"),
        foreignKeys = arrayOf(
                        ForeignKey(entity = CustomLists::class,
                                   parentColumns = arrayOf("id"),
                                   childColumns = arrayOf("customlistName")),
                        ForeignKey(entity = Game::class,
                                   parentColumns = arrayOf("id"),
                                   childColumns = arrayOf("gameId"))
                            )

        )

data class CustomListsGameJoin (
    val customlistName: String,
    val gameId: String,
    val name: String?,
    val yearPublished: Int,
    val minPlayer: Int,
    val maxPlayer: Int,
    val minAge: Int,
    val description: String?,
    val primaryPublisher: String?
)
