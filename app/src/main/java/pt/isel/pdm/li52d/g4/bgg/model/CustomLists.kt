package pt.isel.pdm.li52d.g4.bgg.model;

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CustomLists (
    @PrimaryKey val name: String?

)
