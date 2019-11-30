package pt.isel.pdm.li52d.g4.bgg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation

class CustomListsAndGames(
    @Embedded
    val list: CustomList,
    @Relation(parentColumn = "name", entityColumn = "nameList")
    val gamesList: List<DesignersAndGames>?
)

data class DesignersAndGames(
    @Embedded
    val game: Game,
    @Relation(parentColumn = "name", entityColumn = "gameName")
    val designerList: List<Designer>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Game::class.java.classLoader)!!,
        parcel.createTypedArrayList(Designer)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(game, flags)
        parcel.writeTypedList(designerList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DesignersAndGames> {
        override fun createFromParcel(parcel: Parcel): DesignersAndGames {
            return DesignersAndGames(parcel)
        }

        override fun newArray(size: Int): Array<DesignersAndGames?> {
            return arrayOfNulls(size)
        }
    }
}

class FavoritesFeatures(
    @Embedded
    val listFav: Favorites,
    @Relation(parentColumn = "nameFavList", entityColumn = "nameFavListM")
    val listMechanics: List<Mechanics>,
    @Relation(parentColumn = "nameFavList", entityColumn = "nameFavListC")
    val listCategories: List<Categories>,
    @Relation(parentColumn = "nameFavList", entityColumn = "nameFavListGame")
    val listGamesinFavorites: List<DesignersAndGames>
)