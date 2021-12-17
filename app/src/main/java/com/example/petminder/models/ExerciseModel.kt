package com.example.petminder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel (var id: Long=0,
                          var petId: Long= 0, var name: String="", var length: Int=0,
                          var lat: Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f): Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable