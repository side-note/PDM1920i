package pt.isel.pdm.li52d.g4.bgg.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GameDto(
    val name: String?,
    val images: ImageDto?,
    val artists: Array<String>?,
    @field:SerializedName("year_published") val yearPublished: Int,
    @field:SerializedName("min_players") val minPlayer: Int,
    @field:SerializedName("max_players") val maxPlayer: Int,
    @field:SerializedName("min_age") val minAge: Int,
    @field:SerializedName("description_preview") val description: String?,
    @field:SerializedName("primary_publisher") val primaryPublisher: String?,
    @field:SerializedName("average_user_rating") val avgUserRating: Double,
    @field:SerializedName("official_url") val url: String?,
    @field:SerializedName("rules_url") val rulesUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable<ImageDto>(ImageDto::class.java.classLoader),
        parcel.createStringArray(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeParcelable(images,flags)
        parcel.writeStringArray(artists)
        parcel.writeInt(yearPublished)
        parcel.writeInt(minPlayer)
        parcel.writeInt(maxPlayer)
        parcel.writeInt(minAge)
        parcel.writeString(description)
        parcel.writeString(primaryPublisher)
        parcel.writeDouble(avgUserRating)
        parcel.writeString(url)
        parcel.writeString(rulesUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameDto

        if (name != other.name) return false
        if (yearPublished != other.yearPublished) return false
        if (minPlayer != other.minPlayer) return false
        if (maxPlayer != other.maxPlayer) return false
        if (minAge != other.minAge) return false
        if (description != other.description) return false
        if (images != other.images) return false
        if (primaryPublisher != other.primaryPublisher) return false
        if (artists != null) {
            if (other.artists == null) return false
            if (!artists.contentEquals(other.artists)) return false
        } else if (other.artists != null) return false
        if (avgUserRating != other.avgUserRating) return false
        if (url != other.url) return false
        if (rulesUrl != other.rulesUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + yearPublished
        result = 31 * result + minPlayer
        result = 31 * result + maxPlayer
        result = 31 * result + minAge
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (images?.hashCode() ?: 0)
        result = 31 * result + (primaryPublisher?.hashCode() ?: 0)
        result = 31 * result + (artists?.contentHashCode() ?: 0)
        result = 31 * result + avgUserRating.hashCode()
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (rulesUrl?.hashCode() ?: 0)
        return result
    }

    companion object CREATOR : Parcelable.Creator<GameDto> {
        override fun createFromParcel(parcel: Parcel): GameDto {
            return GameDto(parcel)
        }

        override fun newArray(size: Int): Array<GameDto?> {
            return arrayOfNulls(size)
        }
    }

}