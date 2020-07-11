package com.ofek.moviesexcercise.presentation.objects

import android.os.Parcel
import android.os.Parcelable

class UiMovie(
    var id: Int,
    var title: String = "",
    var image: String = "",
    var rating: Double = 0.0,
    var releaseYear: String = "",
    var overview: String = "",
    var favorite: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readDouble(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeDouble(rating)
        parcel.writeString(releaseYear)
        parcel.writeString(overview)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiMovie> {
        override fun createFromParcel(parcel: Parcel): UiMovie {
            return UiMovie(parcel)
        }

        override fun newArray(size: Int): Array<UiMovie?> {
            return arrayOfNulls(size)
        }
    }
}
