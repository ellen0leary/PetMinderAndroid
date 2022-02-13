package com.example.petminder.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetModel(var id: Long=0,
                    var type : String = "",
                    var name: String = "",
                    var age: Int=0,
                    var weight: Float=0.0f,
                    var image:Uri = Uri.EMPTY ): Parcelable