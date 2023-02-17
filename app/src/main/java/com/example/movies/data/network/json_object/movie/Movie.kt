package com.example.movies.data.network.json_object.movie

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favourite_movies")
@Parcelize
data class Movie (
    @PrimaryKey @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterURL: String,
    @Json(name = "vote_average") val rating: String,
    @Json(name = "title") val name: String,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "overview") val description: String
) : Parcelable