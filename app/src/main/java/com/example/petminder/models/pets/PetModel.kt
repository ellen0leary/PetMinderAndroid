package com.example.petminder.models.pets

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetModel(var uid: String="",
                    var type : String = "",
                    var name: String = "",
                    var age: Int=0,
                    var weight: Float=0.0f,
                    var image:String= ""): Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "uid" to uid,
            "type" to type,
            "name" to name,
            "age" to age,
            "weight" to weight,
            "image" to image
        )
    }
}