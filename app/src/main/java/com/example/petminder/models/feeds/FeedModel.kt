package com.example.petminder.models.feeds

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedModel (var uid: String="",
                      var petId: String ="",
                      var time: String="",
                      var weigth: Float=0.0f,
): Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "petId" to petId,
            "time" to time,
            "weigth" to weigth,
        )
    }
}