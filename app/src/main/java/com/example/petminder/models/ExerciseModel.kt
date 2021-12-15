package com.example.petminder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel (var id: Long=0,
                          var petId: Long= 0, var name: String="", var length: Int=0): Parcelable