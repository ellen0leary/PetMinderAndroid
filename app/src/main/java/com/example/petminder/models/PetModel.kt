package com.example.petminder.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetModel(var id: Long=0,
                    var name: String = "",var image:Uri = Uri.EMPTY ): Parcelable