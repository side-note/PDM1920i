package pt.isel.pdm.li52d.g4.bgg.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "customlist_games_join",
        primaryKeys = arrayOf("customlistId", "gameId"),
        foreignKeys = arrayOf(
                        ForeignKey(entity = CustomLists::class,
                                   parentColumns = arrayOf("id"),
                                   childColumns = arrayOf("customlistId")),
                        ForeignKey(entity = Games::class,
                                   parentColumns = arrayOf("id"),
                                   childColumns = arrayOf("gameId"))
                            )

        )

data class CustomListsGamesJoin (
    val customlistId: Int,
    val gameId: Int
)
