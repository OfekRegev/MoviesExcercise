package com.ofek.moviesexcercise.presentation.objects

import android.os.Parcel
import android.os.Parcelable

class UiMovie(
    var title: String= "",
    var image: String= "", var rating: Double = 0.0, var releaseYear: Int = 0, var genre: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.createStringArrayList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeDouble(rating)
        parcel.writeInt(releaseYear)
        parcel.writeStringList(genre)
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
