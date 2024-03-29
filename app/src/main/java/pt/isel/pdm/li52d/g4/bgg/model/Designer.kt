package pt.isel.pdm.li52d.g4.bgg.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity

@Entity(tableName = "designer", primaryKeys = ["listName","gameName", "designerName"])
data class Designer(
    val listName: String,
    val gameName: String,
    val designerName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(listName)
        parcel.writeString(gameName)
        parcel.writeString(designerName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Designer> {
        override fun createFromParcel(parcel: Parcel): Designer {
            return Designer(parcel)
        }

        override fun newArray(size: Int): Array<Designer?> {
            return arrayOfNulls(size)
        }
    }
}
