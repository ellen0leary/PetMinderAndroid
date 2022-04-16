package com.example.petminder.models.exercises

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel (var uid: String= "",
                          var petId:String ="", var name: String="", var length: Int=0,
                          var lat: Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f): Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "petId" to petId,
            "name" to name,
            "length" to length,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom
        )
    }
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable