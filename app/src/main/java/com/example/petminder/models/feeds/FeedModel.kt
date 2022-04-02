package com.example.petminder.models.feeds

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedModel (var id: Long=0,
                      var petId: String ="",
                      var time: String="",
                      var weigth: Float=0.0f,
): Parcelable