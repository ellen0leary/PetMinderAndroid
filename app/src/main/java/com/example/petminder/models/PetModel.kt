package com.example.petminder.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetModel(var name: String = ""): Parcelable