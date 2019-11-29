package pt.isel.pdm.li52d.g4.bgg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity(tableName = "artist", primaryKeys = ["listName","gameName", "artistName"])
data class Artist(
    val listName: String,
    val gameName: String,
    val artistName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(listName)
        parcel.writeString(gameName)
        parcel.writeString(artistName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }
}
