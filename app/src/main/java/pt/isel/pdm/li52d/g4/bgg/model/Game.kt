package pt.isel.pdm.li52d.g4.bgg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity(tableName = "games", primaryKeys = ["id", "nameList"])
data class Game(
    val id: String,
    var nameList: String,
    val nameFavListGame: String,
    val name: String,
    val desc: String?,
    val rating: Double,
    val year: Int,
    val minplayer: Int,
    val maxplayer: Int,
    val minage: Int,
    val publisher: String?,
    val rulesurl: String?,
    val url: String?,
    val smallImage: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nameList)
        parcel.writeString(nameFavListGame)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeDouble(rating)
        parcel.writeInt(year)
        parcel.writeInt(minplayer)
        parcel.writeInt(maxplayer)
        parcel.writeInt(minage)
        parcel.writeString(publisher)
        parcel.writeString(rulesurl)
        parcel.writeString(url)
        parcel.writeString(smallImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

}