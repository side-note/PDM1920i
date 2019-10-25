package g5.li22d.poo.isel.pt.bgg.dto

import android.os.Parcel
import android.os.Parcelable

data class ImageDto(
    val thumb   : String?,
    val small   : String?,
    val medium  : String?,
    val large   : String?,
    val original: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thumb)
        parcel.writeString(small)
        parcel.writeString(medium)
        parcel.writeString(large)
        parcel.writeString(original)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageDto> {
        override fun createFromParcel(parcel: Parcel): ImageDto {
            return ImageDto(parcel)
        }

        override fun newArray(size: Int): Array<ImageDto?> {
            return arrayOfNulls(size)
        }
    }
}
