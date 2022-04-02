package com.example.petminder.models.exercises

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel (var id: Long=0,
                          var petId:String ="", var name: String="", var length: Int=0,
                          var lat: Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f): Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable