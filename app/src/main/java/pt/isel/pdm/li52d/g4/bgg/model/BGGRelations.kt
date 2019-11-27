package pt.isel.pdm.li52d.g4.bgg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation

class CustomListsAndGames(
    @Embedded
    val list: CustomList,
    @Relation(parentColumn = "name", entityColumn = "nameList")
    val gamesList: List<ArtistsAndGames>?
)

data class ArtistsAndGames(
    @Embedded
    val game: Game,
    @Relation(parentColumn = "name", entityColumn = "gameName")
    val artistList: List<Artist>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Game::class.java.classLoader)!!,
        parcel.createTypedArrayList(Artist)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(game, flags)
        parcel.writeTypedList(artistList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArtistsAndGames> {
        override fun createFromParcel(parcel: Parcel): ArtistsAndGames {
            return ArtistsAndGames(parcel)
        }

        override fun newArray(size: Int): Array<ArtistsAndGames?> {
            return arrayOfNulls(size)
        }
    }
}